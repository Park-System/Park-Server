package parkSystem.park.queue.service.command;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.queue.repository.QueueRedisRepository;
import parkSystem.park.queue.service.facade.QueueService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static parkSystem.park.queue.QueueConst.*;

@SpringBootTest
@Transactional
@Slf4j
class QueueCommandServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    QueueService queueService;
    @Autowired
    QueueRedisRepository queueRedisRepository;
    @Autowired
    @Qualifier("luckyDrawRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setup(){
        // 100명 회원 생성
        for (int i = 1; i <= 80; i++) {
            Member member = new Member("testpwd", "test@test", "user"+i, "test"+i, UserRole.USER, null);
            memberRepository.save(member);
        }
    }

    @AfterEach
    void cleanUp(){
        queueRedisRepository.clear();
    }

    @Test
    @DisplayName("참가열, 대기열 진입 테스트")
    void luckyDrawParticipate(){

        long participantCount1 = queueRedisRepository.getParticipantCount();
        long waitingCount1 = queueRedisRepository.getWaitingCount();
        log.info("{}",participantCount1);
        log.info("{}",waitingCount1);

        //when
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            queueService.LuckyDrawParticipate(member.getUsername());
        }

        Set<String> participants = queueRedisRepository.getParticipants();
        for (String participant : participants) {
            log.info("참가자 : {}", participant);
        }

        Set<String> waiting = queueRedisRepository.getWaiting();
        for (String s : waiting) {
            log.info("대기자 : {}", s);
        }


        //then
        long participantCount = queueRedisRepository.getParticipantCount();
        long waitingCount = queueRedisRepository.getWaitingCount();
        Assertions.assertThat(participantCount).isEqualTo(50);
        Assertions.assertThat(waitingCount).isEqualTo(30);
    }

    @Test
    @DisplayName("럭키드로우 바로 참가 가능 여부 확인")
    void luckDrawParticipateVerify(){

        //given
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            queueService.LuckyDrawParticipate(member.getUsername());
        }

        //when(user5는 참가열 상태, user70은 대기열인 상태)
        boolean user5 = queueService.luckDrawParticipateVerify("user5");
        boolean user70 = queueService.luckDrawParticipateVerify("user70");

        //then
        Assertions.assertThat(user5).isTrue();
        Assertions.assertThat(user70).isFalse();
    }

    @Test
    @DisplayName("럭키드로우 참가 완료 후 참가열 삭제, 대기열 대기자 -> 참가열으로 이동")
    void removeLuckyDrawParticipate(){

        //given
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            queueService.LuckyDrawParticipate(member.getUsername());
        }

        //when(user5, user7, user9 럭키드로우 참가 완료했다고 가정)
        queueService.removeLuckyDrawParticipate("user5");
        queueService.removeLuckyDrawParticipate("user7");
        queueService.removeLuckyDrawParticipate("user9");
        long participantCount = queueRedisRepository.getParticipantCount();
        long waitingCount = queueRedisRepository.getWaitingCount();

        //then
        Assertions.assertThat(participantCount).isEqualTo(50);
        Assertions.assertThat(waitingCount).isEqualTo(27);
    }

    @Test
    @DisplayName("시간이 만료된 참가 삭제 후 대기열 대기자 -> 참가열로 이동")
    void waitingToParticipate() throws InterruptedException {

        //given
        Member member5 = memberRepository.findByUsername("user5").get();
        Member member6 = memberRepository.findByUsername("user6").get();
        Member member7 = memberRepository.findByUsername("user7").get();
        Member member8 = memberRepository.findByUsername("user8").get();

        redisTemplate.opsForZSet().add(PARTICIPANTS_KEY, String.valueOf(member5.getId()), System.currentTimeMillis()+5);
        redisTemplate.opsForZSet().add(WAITING_KEY, String.valueOf(member6.getId()), System.currentTimeMillis());
        redisTemplate.opsForZSet().add(WAITING_KEY, String.valueOf(member7.getId()), System.currentTimeMillis());
        redisTemplate.opsForZSet().add(WAITING_KEY, String.valueOf(member8.getId()), System.currentTimeMillis());
        Thread.sleep(7000);  // 7초 동안 중단

        //when
        queueService.waitingToParticipate();
        long participantCount = queueRedisRepository.getParticipantCount();
        long waitingCount = queueRedisRepository.getWaitingCount();

        //then
        Assertions.assertThat(participantCount).isEqualTo(1);
        Assertions.assertThat(waitingCount).isEqualTo(2);

    }
}
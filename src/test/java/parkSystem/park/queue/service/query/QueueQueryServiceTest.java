package parkSystem.park.queue.service.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.queue.dto.response.QueueWaitingPositionResDTO;
import parkSystem.park.queue.exception.NotFoundWaitingPositionException;
import parkSystem.park.queue.repository.QueueRedisRepository;
import parkSystem.park.queue.service.facade.QueueService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueueQueryServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    QueueService queueService;
    @Autowired
    QueueRedisRepository queueRedisRepository;

    @BeforeEach
    void setup(){
        // 100명 회원 생성
        for (int i = 1; i <= 80; i++) {
            Member member = new Member("testpwd", "test@test", "user"+i, "test"+i, UserRole.USER, null);
            memberRepository.save(member);
            queueService.LuckyDrawParticipate(member.getUsername());
        }
    }

    @AfterEach
    void cleanUp(){
        queueRedisRepository.clear();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("대기자 순번 출력 테스트")
    void getWaitingPosition(){

        //given
        Member user51 = memberRepository.findByUsername("user51").get();
        Member user52 = memberRepository.findByUsername("user52").get();
        Member user60 = memberRepository.findByUsername("user60").get();

        //when
        QueueWaitingPositionResDTO waitingPosition51 = queueService.getWaitingPosition(user51.getId());
        QueueWaitingPositionResDTO waitingPosition52 = queueService.getWaitingPosition(user52.getId());
        QueueWaitingPositionResDTO waitingPosition60 = queueService.getWaitingPosition(user60.getId());

        //then
        Assertions.assertThat(waitingPosition51.rank()).isEqualTo(1);
        Assertions.assertThat(waitingPosition52.rank()).isEqualTo(2);
        Assertions.assertThat(waitingPosition60.rank()).isEqualTo(10);

    }

    @Test
    @DisplayName("대기자 순번 출력 테스트(대기자 명단에 없을 경우)")
    void getWaitingPosition1(){

        //given
        Member user1 = memberRepository.findByUsername("user1").get();

        //then
        assertThrows(NotFoundWaitingPositionException.class, ()->
                queueService.getWaitingPosition(user1.getId()));

    }
}
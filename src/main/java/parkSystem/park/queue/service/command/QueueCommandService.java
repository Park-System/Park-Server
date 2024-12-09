package parkSystem.park.queue.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.exception.NotFoundMemberException;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.queue.dto.response.QueueParticipateResDTO;
import parkSystem.park.queue.repository.QueueRedisRepository;

import java.util.List;
import java.util.Set;

import static parkSystem.park.queue.QueueConst.MAX_PARTICIPANTS;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QueueCommandService {

    private final QueueRedisRepository queueRedisRepository;
    private final MemberRepository memberRepository;

    public QueueParticipateResDTO luckDrawParticipateVerify(String username){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        boolean participateOK = false;
        Set<String> participants = queueRedisRepository.getParticipants();

        participateOK = participants.stream()
                .map(Long::valueOf)
                .anyMatch(memberId -> memberId.equals(member.getId())); // member.getId()와 일치하는지 체크

        return QueueParticipateResDTO.toDTO(member, participateOK);
    }

    public void luckyDrawParticipate(String username){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        long participantCount = queueRedisRepository.getParticipantCount();
        log.info("참가자 카운트 : {}", participantCount);

        if (participantCount < MAX_PARTICIPANTS) {
            // 참여자 수가 50명 미만일 때, 참여자로 추가
            queueRedisRepository.addParticipant(String.valueOf(member.getId()));
        } else {
            // 참여자 수가 50명을 초과하면 대기열에 추가
            queueRedisRepository.addWaiting(String.valueOf(member.getId()));
        }
    }

    public void removeLuckyDrawParticipate(String username){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        //참가자 삭제
        Long removedCount = queueRedisRepository.removeParticipant(String.valueOf(member.getId()));

        //웨이팅 큐에서 대기자 한명을 꺼내어 참여자로 전환
        if(removedCount>0){
            List<Long> waitingList = queueRedisRepository.popFromWaitingList(removedCount.intValue());
            if(waitingList!=null){
                waitingList.forEach(memberId ->
                        queueRedisRepository.addParticipant(String.valueOf(memberId)));
            }
        }

    }

    public void waitingToParticipate(){
        Long expiredCount = queueRedisRepository.removeExpireParticipants();
        if(expiredCount==0) return;

        List<Long> waitingList = queueRedisRepository.popFromWaitingList(expiredCount.intValue());
        if(waitingList!=null){
            waitingList.forEach(memberId ->
                    queueRedisRepository.addParticipant(String.valueOf(memberId)));
        }
    }
}

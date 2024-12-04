package parkSystem.park.queue.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.luckDraw.exception.NotFoundMemberException;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.queue.repository.QueueRedisRepository;

import static parkSystem.park.queue.QueueConst.MAX_PARTICIPANTS;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QueueCommandService {

    private final QueueRedisRepository queueRedisRepository;
    private final MemberRepository memberRepository;

    public void LuckyDrawParticipate(String username, Long luckDrawId){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        long participantCount = queueRedisRepository.getParticipantCount();

        if (participantCount < MAX_PARTICIPANTS) {
            // 참여자 수가 50명 미만일 때, 참여자로 추가
            queueRedisRepository.addParticipant(String.valueOf(member.getId()));
        } else {
            // 참여자 수가 50명을 초과하면 대기열에 추가
            queueRedisRepository.addWaiting(String.valueOf(member.getId()));
        }
    }

    public void removeLuckyDrawParticipate(String username, Long luckDrawId){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundMemberException("멤버가 존재 하지 않습니다."));

        //참가자 삭제
        boolean removed = queueRedisRepository.removeParticipant(String.valueOf(member.getId()));

        //웨이팅 큐에서 대기자 한명을 꺼내어 참여자로 전환
        if(removed){
            Long memberId = queueRedisRepository.popFromWaitingList();
            if(memberId!=null){
                queueRedisRepository.addParticipant(String.valueOf(memberId));
            }
        }

    }
}

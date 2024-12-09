package parkSystem.park.queue.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.queue.dto.response.QueueParticipateResDTO;
import parkSystem.park.queue.dto.response.QueueWaitingPositionResDTO;
import parkSystem.park.queue.service.command.QueueCommandService;
import parkSystem.park.queue.service.query.QueueQueryService;

@Service
@Transactional
@RequiredArgsConstructor
public class QueueService {

    private final QueueCommandService queueCommandService;
    private final QueueQueryService queueQueryService;

    /**
     * 럭키 드로우 참여 대기열 존재 여부 확인
     */
    public QueueParticipateResDTO luckDrawParticipateVerify(String username){
        return queueCommandService.luckDrawParticipateVerify(username);
    }

    /**
     * 럭키 드로우 대기열 진입
     */
    public void LuckyDrawParticipate(String username){
        queueCommandService.luckyDrawParticipate(username);
    }

    /**
     * 럭키 드로우 참가 완료한 경우, 참가열에서 제거 + 대기열 대기자 -> 참가열로 이동시키는 서비스
     */
    public void removeLuckyDrawParticipate(String username){
        queueCommandService.removeLuckyDrawParticipate(username);
    }

    /**
     * 참가열에서 유효시간이 만료된 참가자를 삭제하고, 대기열 대기자 -> 참가열로 이동시키는 서비스(스케쥴러에 의해 동작)
     */
    public void waitingToParticipate(){
        queueCommandService.waitingToParticipate();
    }

    /**
     * 사용자 대기열 대기 순번 조회
     */
    public QueueWaitingPositionResDTO getWaitingPosition(Long memberId){
        return queueQueryService.getWaitingPosition(memberId);
    }
}

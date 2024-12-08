package parkSystem.park.queue.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.queue.service.command.QueueCommandService;

@Service
@Transactional
@RequiredArgsConstructor
public class QueueService {

    private final QueueCommandService queueCommandService;

    /**
     * 럭키 드로우 참여 대기열 존재 여부 확인
     */
    public boolean luckDrawParticipateVerify(String username){
        return queueCommandService.luckDrawParticipateVerify(username);
    }

    /**
     * 럭키 드로우 대기열 진입
     */
    public void LuckyDrawParticipate(String username){
        queueCommandService.luckyDrawParticipate(username);
    }

    public void removeLuckyDrawParticipate(String username){
        queueCommandService.removeLuckyDrawParticipate(username);
    }

    public void waitingToParticipate(){
        queueCommandService.waitingToParticipate();
    }
}

package parkSystem.park.queue.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parkSystem.park.queue.service.facade.QueueService;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final QueueService queueService;

    /**
     * 3초마다 스케쥴러가 돌면서, 참가열에 만료된 참가자를 제거 후 대기열 대기자 -> 참가열로 이동
     */
    @Scheduled(fixedRate = 3 * 1000) //3초마다 스케쥴링
    public void updateParticipateQueue(){
        queueService.waitingToParticipate();
    }
}

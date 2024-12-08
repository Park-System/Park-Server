package parkSystem.park.queue.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parkSystem.park.queue.service.facade.QueueService;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final QueueService queueService;

    @Scheduled(fixedRate = 3 * 1000) //3초마다 스케쥴링
    public void updateParticipateQueue(){
        queueService.waitingToParticipate();
    }
}

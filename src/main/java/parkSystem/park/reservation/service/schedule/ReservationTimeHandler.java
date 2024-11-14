package parkSystem.park.reservation.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parkSystem.park.reservation.service.facade.ReservationService;

import static parkSystem.park.reservation.ReservationConst.SCHEDULED_CANCEL_ROLLBACK_TIME;
import static parkSystem.park.reservation.ReservationConst.SCHEDULED_CANCEL_TIME;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationTimeHandler {

    private final ReservationService reservationService;


    /**
     * 1분 마다 결제 실패 로직을 체크후에  결제 실패상태이면 해당 에약 상태를 즉 예약 자리와 예약 상태를 롤백
     */

    @Scheduled(fixedRate = SCHEDULED_CANCEL_ROLLBACK_TIME)
    public void rollBackReservation(){
        reservationService.bulkReservation();
    }

    /**
     * 테스트를 위해 2초마다 테스트 대기 상태 있을시 실패로 업데이트 레디스가 죽었을 가능성을 대비해
     */
    @Scheduled(fixedRate = SCHEDULED_CANCEL_TIME)
    public void rollBackCancelStatus(){
        reservationService.bulkUpdateCancel();
    }





}

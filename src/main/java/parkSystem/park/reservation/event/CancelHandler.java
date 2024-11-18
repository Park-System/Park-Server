package parkSystem.park.reservation.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import parkSystem.park.reservation.service.command.ReservationCommandService;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelHandler {

    private final ReservationCommandService reservationCommandService;


    @EventListener
    public void scheduleReservationCancel(CancelEvent cancelEvent) {
        log.info("이벤트 실행");
        reservationCommandService.cancelReservation(cancelEvent.getReservationId());
    }
}

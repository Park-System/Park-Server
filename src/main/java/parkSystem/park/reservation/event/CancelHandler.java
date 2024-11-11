package parkSystem.park.reservation.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import parkSystem.park.common.config.exception.NotFoundException;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static parkSystem.park.reservation.ReservationConst.NOT_FOUND_RESERVATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelHandler {

    private final ReservationRepository reservationRepository;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);


    /**
     * ScheduledExecutorService(jdk) 의 스레드 풀 기반의 스케줄러 사용
     *
     * 예약 시간 20분으로 두고 에약 실패 시에 20 분후 예약 실패 상태로 돌림
     *
     * @Transactional 메서드 단위로 설정한 이유는 스케줄러는 비동기로 처리 되기 때문에 다른 스레드를 사용한다.
     * 그래서 트랜잭션 범위가 적용이 안됨
     *
     *
     */


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void scheduleReservationCancel(CancelEvent cancelEvent) {

        Reservation reservation = cancelEvent.getReservation();


        log.info("예약 취소");

        executorService.schedule(() -> {
            try {
                log.info("reservationId = {}", reservation.getId());

                Reservation findReservation = reservationRepository.findById(reservation.getId())
                        .orElseThrow(() -> new NotFoundException(NOT_FOUND_RESERVATION));

                // 상태가 WAIT일 경우에만 실패 처리
                if (findReservation != null && findReservation.getStatus() == ReservationStatus.WAIT) {
                    findReservation.failDeposit();
                    reservationRepository.save(findReservation);  // 영속성 컨텍스트에 반영

                    log.info("예약 상태 취소 변경");
                } else {
                    log.info("예약 상태가 WAIT이 아니므로 취소되지 않음");
                }
            } catch (Exception e) {
                log.error("예약 취소 처리 중 오류 발생", e);
            } finally {
                log.info("스케줄러 작업 완료");
            }
        }, 3, TimeUnit.SECONDS);
    }
}

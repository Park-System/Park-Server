package parkSystem.park.reservation.event;


import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import parkSystem.park.reservation.service.command.ReservationDepositService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventHandler {

    private final ReservationDepositService reservationDepositService;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void paymentDeposit(DepositEvent depositEvent) throws IamportResponseException, IOException {

        try{
            reservationDepositService.depositReservation(depositEvent.getReservationId(), depositEvent.getImPortId());
        }
        catch (IamportResponseException | IOException e) {
            log.error("결제 중 오류 발생: 예약 ID {}", depositEvent.getReservationId(), e);
            // 결제 실패 시 필요한 추가 처리를 여기에 구현
        }

    }

}

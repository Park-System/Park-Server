package parkSystem.park.reservation.service.command;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.service.query.ReservationQueryService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationDepositService {

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretKey}")
    private String apiSecret;

    private final ReservationQueryService reservationQueryService;
    
    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @Transactional
    public void depositReservation(Long reservationId, String imp_uid) throws IamportResponseException, IOException {
        Payment payment = iamportClient.paymentByImpUid(imp_uid).getResponse();

        Reservation findByReservation = reservationQueryService.findReservationById(reservationId); //예약찾음

        if(payment != null &&  payment.getStatus().equals("paid")) {
            findByReservation.successDeposit(); //예약 상태 변경
            log.info("결제 상태 = {}", findByReservation.getStatus());
        }
        else{
            findByReservation.failDeposit();
            log.info("결제 실패");
        }

    }
}

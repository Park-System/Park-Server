package parkSystem.park.reservation.service.command;


import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.reservation.controller.dto.DepositReqDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
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
    public void depositReservation(DepositReqDTO depositResDTO) throws IamportResponseException, IOException {
        Payment payment = iamportClient.paymentByImpUid(depositResDTO.importId()).getResponse();

        Reservation findByReservation = reservationQueryService.findReservationById(depositResDTO.reservationId()); //예약찾음


        if(findByReservation.getStatus() != ReservationStatus.WAIT) {
            findByReservation.failDeposit();
            log.info("시간 초과로 인해 결제가 실패했습니다.");

            //아임 포트 결제 취소
            iamportClient.cancelPaymentByImpUid(new CancelData(depositResDTO.importId(), true));
            return;
        }

        if(payment != null &&  payment.getStatus().equals("paid")) {
            findByReservation.successDeposit(); //예약 상태 변경
            log.info("결제 상태 = {}", findByReservation.getStatus());
        }
        else{
            findByReservation.failDeposit();
            log.info("결제 실패");
            return;
        }

    }
}

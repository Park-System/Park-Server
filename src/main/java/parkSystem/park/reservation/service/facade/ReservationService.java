package parkSystem.park.reservation.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.service.command.ReservationCommandService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationCommandService reservationCommandService;


    public ReservationResDTO reservation(ReservationReqDTO reqDTO) {

       return reservationCommandService.reservation(reqDTO);
    }
}

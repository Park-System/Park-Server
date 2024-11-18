package parkSystem.park.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.config.exception.ImportException;
import parkSystem.park.reservation.controller.dto.DepositReqDTO;
import parkSystem.park.reservation.controller.dto.DepositResDTO;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.service.command.ReservationDepositService;
import parkSystem.park.reservation.service.facade.ReservationService;

import static parkSystem.park.reservation.ReservationConst.SUCCESS_DEPOSIT;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    private final ReservationDepositService reservationDepositService;


    @PostMapping()
    public ResponseEntity<ReservationResDTO> reservation(@RequestBody ReservationReqDTO reservationReqDTO){

        log.info("예약 컨트롤러");
        ReservationResDTO reservation = reservationService.reservation(reservationReqDTO);

        return ResponseEntity.ok(reservation);
    }


    @PostMapping("/deposit")
    public ResponseEntity<DepositResDTO> deposit(@RequestBody DepositReqDTO depositResDTO)  {

        log.info("보증금 컨트롤러");
        try{
            reservationDepositService.depositReservation(depositResDTO);
            return ResponseEntity.ok(DepositResDTO.success(SUCCESS_DEPOSIT));
        }
        catch (Exception e) {
            throw new ImportException();
        }


    }

}

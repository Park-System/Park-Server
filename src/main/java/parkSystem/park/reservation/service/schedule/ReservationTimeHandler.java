package parkSystem.park.reservation.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parkSystem.park.park.service.command.ParkingInfoCommandService;
import parkSystem.park.park.service.command.ParkingSpotCommandService;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationTimeHandler {

    private final ReservationRepository reservationRepository;

    private final ParkingInfoCommandService parkingInfoCommandService;

    private final ParkingSpotCommandService parkingSpotCommandService;


    /**
     * 1분 마다 결제 실패 로직을 체크후에  결제 실패상태이면 해당 에약 상태를 즉 예약 자리와 예약 상태를 롤백
     */

    @Scheduled(fixedRate = 60000)
    public void rollBackReservation(){

        List<Reservation> reservationList = reservationRepository.findByStatus(ReservationStatus.FAIL);

        parkingSpotCommandService.updateParkingSpotAvailable(reservationList);

        parkingInfoCommandService.updateParkingInfoAmount(reservationList);
    }



}

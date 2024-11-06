package parkSystem.park.reservation.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.service.command.ReservationCommandService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationCommandService reservationCommandService;


    public void reservation(Reservation reservation) {}


}

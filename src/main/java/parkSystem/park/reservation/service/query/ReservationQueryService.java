package parkSystem.park.reservation.service.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.common.config.exception.NotFoundException;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.repository.ReservationRepository;

import static parkSystem.park.reservation.ReservationConst.NOT_FOUND_RESERVATION;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;


    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_RESERVATION));
    }
}

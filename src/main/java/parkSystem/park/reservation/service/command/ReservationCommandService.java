package parkSystem.park.reservation.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.reservation.repository.ReservationRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;


    public void reservation(Long carId, Long reservationId){

    }










}

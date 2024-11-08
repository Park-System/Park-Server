package parkSystem.park.park.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.repository.ParkingSpotRepository;
import parkSystem.park.reservation.domain.Reservation;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ParkingSpotCommandService {

    private final ParkingSpotRepository parkingSpotRepository;


    public void updateParkingSpotAvailable(List<Reservation> reservations){
        List<Long> longs = reservations.stream().map(Reservation::getId).toList();

        parkingSpotRepository.updateSpotAvailable(longs);
    }
}

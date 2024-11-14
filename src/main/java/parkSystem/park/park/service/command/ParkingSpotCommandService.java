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
        log.info("Parking Spot is available");
        List<Long> longs = reservations.stream().map(ParkingSpotCommandService::apply).toList();



        parkingSpotRepository.updateSpotAvailable(longs);
    }

    private static Long apply(Reservation reservation) {
        return reservation.getParkingSpot().getId();
    }
}

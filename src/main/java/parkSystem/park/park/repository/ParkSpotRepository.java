package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.park.domain.ParkingSpot;

public interface ParkSpotRepository extends JpaRepository<ParkingSpot, Long> {
}

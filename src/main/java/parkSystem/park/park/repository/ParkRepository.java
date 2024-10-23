package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.park.domain.ParkingInfo;

public interface ParkRepository extends JpaRepository<ParkingInfo, Long> {
}

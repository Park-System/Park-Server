package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.park.domain.ParkingInfo;

public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Long> {
}

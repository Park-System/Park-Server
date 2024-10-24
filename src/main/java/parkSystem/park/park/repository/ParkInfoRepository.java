package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import parkSystem.park.park.domain.ParkingInfo;

import java.util.List;

public interface ParkInfoRepository extends JpaRepository<ParkingInfo, Long> {
}

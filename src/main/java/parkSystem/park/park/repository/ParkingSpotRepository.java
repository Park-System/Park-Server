package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import parkSystem.park.park.domain.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

  @Query("select ps from ParkingSpot  ps where ps.parkingInfo.id = :parkingInfoId")
  Optional<List<ParkingSpot>> findByParkingInfoId(Long parkingInfoId);
}
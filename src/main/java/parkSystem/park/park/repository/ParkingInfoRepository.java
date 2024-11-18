package parkSystem.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parkSystem.park.park.domain.ParkingInfo;

public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update ParkingInfo pi set pi.parkingAmount = pi.parkingAmount + :reservationSize where pi.id = :parkingInfoId")
    void updateParkingAmount(@Param("reservationSize") int amountSize, @Param("parkingInfoId") Long parkingInfoId);
}

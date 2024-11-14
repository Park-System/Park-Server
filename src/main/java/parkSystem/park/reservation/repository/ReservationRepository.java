package parkSystem.park.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r FROM Reservation r join fetch r.parkingSpot where r.status = :status")
    List<Reservation> findByStatus(@Param("status") ReservationStatus status);


    @Query("select r FROM Reservation r where r.status = :status and  r.limitDepositTime < CURRENT_TIMESTAMP ")
    List<Reservation> findByLimitDepositTime(@Param("status") ReservationStatus status);

    @Modifying(clearAutomatically = true)
    @Query("update Reservation r set r.status =  :status WHERE r.id IN :reservationIds")
    void updateStatus(@Param("status") ReservationStatus status ,@Param("reservationIds") List<Long> reservationIds);

}

package parkSystem.park.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r FROM Reservation r where r.status = :status")
    List<Reservation> findByStatus(@Param("status") ReservationStatus status);









}

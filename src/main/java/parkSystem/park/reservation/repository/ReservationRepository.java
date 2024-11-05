package parkSystem.park.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.reservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

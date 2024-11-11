package parkSystem.park.car;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.car.domain.Cars;

public interface CarRepository extends JpaRepository<Cars, Long> {
}

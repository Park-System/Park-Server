package parkSystem.park.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.car.domain.Cars;

public interface CarsRepository extends JpaRepository<Cars, Long> {
}

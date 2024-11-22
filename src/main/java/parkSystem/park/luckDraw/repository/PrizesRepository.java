package parkSystem.park.luckDraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkSystem.park.luckDraw.domain.Prizes;

@Repository
public interface PrizesRepository extends JpaRepository<Prizes, Long> {
}

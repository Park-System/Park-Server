package parkSystem.park.luckDraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkSystem.park.luckDraw.domain.LuckDraw;

@Repository
public interface LuckyDrawRepository extends JpaRepository<LuckDraw, Long> {
}

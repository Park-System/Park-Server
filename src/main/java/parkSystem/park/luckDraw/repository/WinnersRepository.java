package parkSystem.park.luckDraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import parkSystem.park.luckDraw.domain.Winners;

import java.util.List;

@Repository
public interface WinnersRepository extends JpaRepository<Winners, Long> {

    @Query("select w from Winners w where w.luckDraw.id = :luckDrawId")
    List<Winners> findByLuckDrawId(@Param("luckDrawId") Long luckDrawId);
}

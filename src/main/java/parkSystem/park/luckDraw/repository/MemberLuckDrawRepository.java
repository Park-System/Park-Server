package parkSystem.park.luckDraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import parkSystem.park.luckDraw.domain.MemberLuckDraw;

import java.util.List;

@Repository
public interface MemberLuckDrawRepository extends JpaRepository<MemberLuckDraw, Long> {

    @Query("select p from MemberLuckDraw p where p.luckDraw.id = :luckDrawId")
    List<MemberLuckDraw> findByLuckDrawId(@Param("luckDrawId") Long luckDrawId);
}

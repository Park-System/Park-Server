package parkSystem.park.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkSystem.park.coupon.domain.CouponEvent;

@Repository
public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {
}

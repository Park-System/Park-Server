package parkSystem.park.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkSystem.park.coupon.domain.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}

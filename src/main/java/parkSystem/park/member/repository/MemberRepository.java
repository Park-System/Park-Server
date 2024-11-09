package parkSystem.park.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkSystem.park.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByUsername(String username);
}

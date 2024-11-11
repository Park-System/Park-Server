package parkSystem.park.member.service.command;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.member.domain.Member;

@Service
@Transactional
public class CustomUserDetailsCommandService {
  public UserDetails createUserDetails(Member member) {
    return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .roles(member.getRole().toString(), member.getType().toString())
            .build();
  }
}

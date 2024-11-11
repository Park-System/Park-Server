package parkSystem.park.member.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import parkSystem.park.member.domain.Member;
import parkSystem.park.member.service.command.CustomUserDetailsCommandService;
import parkSystem.park.member.service.query.CustomUserDetailsQueryService;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomUserDetailsCommandService customUserDetailsCommandService;
  private final CustomUserDetailsQueryService customUserDetailsQueryService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return customUserDetailsQueryService.loadUserByUsername(username);
  }

  public UserDetails createUserDetails(Member member) {
    return customUserDetailsCommandService.createUserDetails(member);
  }
}
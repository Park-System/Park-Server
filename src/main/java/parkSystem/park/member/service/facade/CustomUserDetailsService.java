package parkSystem.park.member.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findByUsername(username)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
  }

  private UserDetails createUserDetails(Member member) {
    return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .roles(member.getRole().toString(), member.getType().toString())
            .build();
  }
}
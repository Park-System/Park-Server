package parkSystem.park.member.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import parkSystem.park.member.repository.MemberRepository;
import parkSystem.park.member.service.command.CustomUserDetailsCommandService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsQueryService {

  private final MemberRepository memberRepository;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findByUsername(username)
            .map(new CustomUserDetailsCommandService()::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
  }
}

package parkSystem.park.member.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.jwt.JwtToken;
import parkSystem.park.jwt.JwtTokenProvider;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.domain.enums.UserType;
import parkSystem.park.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public JwtToken logIn(String username, String password) {
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

    Authentication authentication =
            authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    return jwtTokenProvider.generateToken(authentication);
  }

  @Transactional
  @Override
  public void registerMember(String username, String password, String email, String nice_name, UserRole role, @Nullable UserType type) {
    String encodedPassword = passwordEncoder.encode(password);
    Member member = new Member(encodedPassword, email, username, nice_name, role, type);
    memberRepository.save(member);
  }
}

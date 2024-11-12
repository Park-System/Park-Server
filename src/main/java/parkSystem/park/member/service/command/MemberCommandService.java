package parkSystem.park.member.service.command;

import lombok.RequiredArgsConstructor;
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
import parkSystem.park.member.dto.LogInDTO;
import parkSystem.park.member.dto.MemberDTO;
import parkSystem.park.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {
  private final MemberRepository memberRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public JwtToken logIn(LogInDTO logInDTO) {
    String username = logInDTO.username();
    String password = logInDTO.password();

    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

    Authentication authentication =
            authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    return jwtTokenProvider.generateToken(authentication, username);
  }

  @Transactional
  public Member registerMember(MemberDTO memberDTO) {
    String password = memberDTO.password();
    String email = memberDTO.email();
    String username = memberDTO.username();
    String nice_name = memberDTO.nice_name();
    UserRole role = memberDTO.role();
    UserType type = memberDTO.type();

    return memberRepository.save(new Member(passwordEncoder.encode(password),
                              email, username, nice_name, role, type));
  }
}

package parkSystem.park.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import parkSystem.park.jwt.JwtToken;
import parkSystem.park.member.dto.LogInDTO;
import parkSystem.park.member.dto.RegistrationDTO;
import parkSystem.park.member.service.MemberServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberServiceImpl memberServiceImpl;

  @Transactional(readOnly = true)
  @PostMapping("/login")
  public JwtToken logIn(@RequestBody LogInDTO logInDTO) {
    String username = logInDTO.username();
    String password = logInDTO.password();
    JwtToken jwtToken = memberServiceImpl.logIn(username, password);

    log.info("{}, 로그인이 완료되었습니다.", username, password);

    return jwtToken;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerMember(@RequestBody RegistrationDTO registrationDTO) {
    memberServiceImpl.registerMember(
            registrationDTO.username(),
            registrationDTO.password(),
            registrationDTO.email(),
            registrationDTO.nice_name(),
            registrationDTO.role(),
            registrationDTO.type()
    );
    return ResponseEntity.ok(registrationDTO.username() + " 회원가입이 완료되었습니다.");
  }
}
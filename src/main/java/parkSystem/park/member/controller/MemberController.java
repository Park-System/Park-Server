package parkSystem.park.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import parkSystem.park.jwt.JwtToken;
import parkSystem.park.member.dto.LogInDTO;
import parkSystem.park.member.dto.MemberDTO;
import parkSystem.park.member.service.facade.MemberService;
import parkSystem.park.member.domain.Member;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  @Transactional(readOnly = true)
  @PostMapping("/login")
  public JwtToken logIn(@RequestBody LogInDTO logInDTO) {
    JwtToken jwtToken = memberService.logIn(logInDTO);

    log.info("{}, 로그인이 완료되었습니다.", logInDTO.username());

    return jwtToken;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerMember(@RequestBody MemberDTO memberDTO) {
    Member member = memberService.registerMember(memberDTO);
    return ResponseEntity.ok(member.getUsername() + " 회원가입이 완료되었습니다.");
  }
}
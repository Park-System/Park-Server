package parkSystem.park.member.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import parkSystem.park.jwt.JwtToken;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.dto.LogInDTO;
import parkSystem.park.member.dto.MemberDTO;
import parkSystem.park.member.service.command.MemberCommandService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  // private final MemberQueryService memberQueryService;
  private final MemberCommandService memberCommandService;

  @Transactional
  public JwtToken logIn(LogInDTO logInDTO) {
    return memberCommandService.logIn(logInDTO);
  }

  @Transactional
  public Member registerMember(MemberDTO memberDTO) {
    return memberCommandService.registerMember(memberDTO);
  }
}
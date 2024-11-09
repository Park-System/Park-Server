package parkSystem.park.member.service;

import jakarta.annotation.Nullable;
import parkSystem.park.jwt.JwtToken;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.domain.enums.UserType;

public interface MemberService {
  public JwtToken logIn(String username, String password);
  public void registerMember(String username, String password, String email, String nice_name, UserRole role, @Nullable UserType status);
}

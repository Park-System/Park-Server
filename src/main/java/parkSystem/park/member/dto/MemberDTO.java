package parkSystem.park.member.dto;

import jakarta.annotation.Nullable;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.domain.enums.UserType;

public record MemberDTO(String username, String password, String email, String nice_name, UserRole role, @Nullable UserType type) {}
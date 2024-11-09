package parkSystem.park.member.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.domain.enums.UserType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String nice_name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Nullable  // 특수한 상태를 가지지 않는 일반 회원 또는 관리자의 경우 null 값을 가짐
    @Enumerated(EnumType.STRING)
    private UserType type;

    public Member(String password, String email, String username, String nice_name, UserRole role, @Nullable UserType type) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.nice_name = nice_name;
        this.role = role;
        this.type = type;
    }
}
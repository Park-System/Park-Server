package parkSystem.park.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.member.domain.enums.UserRole;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Member(String password, String email, String username, String nice_name, UserRole role) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.nice_name = nice_name;
        this.role = role;
    }














}

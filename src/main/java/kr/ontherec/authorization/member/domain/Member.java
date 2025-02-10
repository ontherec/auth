package kr.ontherec.authorization.member.domain;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * login id or open id
     */
    @Column(unique = true, updatable = false, nullable = false)
    private String username;

    private String password;

    private String name;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String picture;

    @OneToMany(mappedBy = "member", fetch = EAGER, cascade = ALL, orphanRemoval = true)
    private Set<Role> roles;
}

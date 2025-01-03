package kr.ontherec.authorization.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "members")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * login id or open id
     */
    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    private String picture;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private Set<Role> roles;
}

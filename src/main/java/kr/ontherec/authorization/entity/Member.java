package kr.ontherec.authorization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * login id or open id
     */
    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    @Column(unique = true, nullable = false, length = 15)
    private String nickname;

    private String imageUrl;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;
}

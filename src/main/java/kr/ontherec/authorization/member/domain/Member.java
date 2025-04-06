package kr.ontherec.authorization.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
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

    @Column(unique = true)
    private String phoneNumber;

    private String picture;

    @ElementCollection(fetch = EAGER)
    @Enumerated(STRING)
    @Column
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime modifiedAt = LocalDateTime.now();
}

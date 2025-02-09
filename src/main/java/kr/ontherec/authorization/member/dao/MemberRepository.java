package kr.ontherec.authorization.member.dao;

import java.util.Optional;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.exception.MemberException;
import kr.ontherec.authorization.member.exception.MemberExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Member> findByUsername(String username);

    default Member findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND));
    }

    void deleteByUsername(String username);
}

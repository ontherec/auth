package kr.ontherec.authorization.member.application;

import kr.ontherec.authorization.member.domain.Member;

public interface MemberService {
    Member getByUsername(String username);
    void register(Member member);
}
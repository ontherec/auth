package kr.ontherec.authorization.member.application;

import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;

public interface MemberService {
    Member getByUsername(String username);
    Member signUp(Member member);
    void update(String username, MemberUpdateRequestDto dto);
    void withdraw(String username);
}
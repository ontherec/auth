package kr.ontherec.authorization.service;

import kr.ontherec.authorization.entity.Member;

public interface MemberService {
    Member findByUsername(String username);
    void createIfNotExists(Member member);
}
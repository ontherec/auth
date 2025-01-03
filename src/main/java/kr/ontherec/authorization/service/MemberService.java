package kr.ontherec.authorization.service;

import kr.ontherec.authorization.entity.Member;

public interface MemberService {
    void createIfNotExists (Member member);
}
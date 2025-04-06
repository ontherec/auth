package kr.ontherec.authorization.infra.fixture;

import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static kr.ontherec.authorization.member.domain.Role.*;

@Component
public class MemberFactory {
    @Autowired private MemberService memberService;

    public Member create(String name, String phoneNumber) {
        Member newMember = Member.builder()
                .username(name)
                .nickname(name)
                .name(name)
                .phoneNumber(phoneNumber)
                .picture("https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg")
                .roles(new HashSet<>(Set.of(GUEST, HOST, ADMIN)))
                .build();

        return memberService.signUp(newMember);
    }
}

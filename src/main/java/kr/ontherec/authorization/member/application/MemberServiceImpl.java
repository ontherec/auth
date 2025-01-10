package kr.ontherec.authorization.member.application;

import kr.ontherec.authorization.member.application.mapper.MemberMapper;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member getByUsername(String username) {
        return memberRepository.findByUsernameOrThrow(username);
    }

    @Override
    public void saveMember(Member member) {
        if (!memberRepository.existsByUsername(member.getUsername())) {
            memberRepository.save(member);
            return;
        }

        Member findMember = memberRepository.findByUsernameOrThrow(member.getUsername());
        MemberMapper.INSTANCE.update(member, findMember);
        memberRepository.save(findMember);
    }
}

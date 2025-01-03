package kr.ontherec.authorization.service;

import kr.ontherec.authorization.entity.Member;
import kr.ontherec.authorization.repository.MemberRepository;
import kr.ontherec.authorization.service.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsernameOrThrow(username);
    }

    @Override
    public void createIfNotExists(Member member) {
        if (!memberRepository.existsByUsername(member.getUsername())) {
            memberRepository.save(member);
            return;
        }

        Member findMember = memberRepository.findByUsernameOrThrow(member.getUsername());
        MemberMapper.INSTANCE.update(member, findMember);
        memberRepository.save(findMember);
    }
}

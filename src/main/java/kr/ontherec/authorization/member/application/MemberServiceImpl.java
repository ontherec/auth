package kr.ontherec.authorization.member.application;

import kr.ontherec.authorization.member.dao.MemberRepository;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.exception.MemberException;
import kr.ontherec.authorization.member.exception.MemberExceptionCode;
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
    public void register(Member member) {
        if (!memberRepository.existsByUsername(member.getUsername())) {
            if(memberRepository.existsByNickname(member.getNickname()))
                throw new MemberException(MemberExceptionCode.EXIST_NICKNAME);

            if(memberRepository.existsByPhoneNumber(member.getPhoneNumber()))
                throw new MemberException(MemberExceptionCode.EXIST_PHONE_NUMBER);

            memberRepository.save(member);
        }
    }
}

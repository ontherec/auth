package kr.ontherec.authorization.member.application;

import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_NICKNAME;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_PHONE_NUMBER;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_USERNAME;

import kr.ontherec.authorization.member.dao.MemberRepository;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import kr.ontherec.authorization.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper mapper = MemberMapper.INSTANCE;

    @Override
    @Transactional(readOnly = true)
    public Member getByUsername(String username) {
        return memberRepository.findByUsernameOrThrow(username);
    }

    @Override
    public Member signUp(Member member) {
        if (memberRepository.existsByUsername(member.getUsername()))
            throw new MemberException(EXIST_USERNAME);

        if(memberRepository.existsByNickname(member.getNickname()))
            throw new MemberException(EXIST_NICKNAME);

        if(memberRepository.existsByPhoneNumber(member.getPhoneNumber()))
            throw new MemberException(EXIST_PHONE_NUMBER);

        return memberRepository.save(member);
    }

    @Override
    public void update(String username, MemberUpdateRequestDto dto) {
        if(dto.nickname() != null && memberRepository.existsByNickname(dto.nickname()))
            throw new MemberException(EXIST_NICKNAME);

        if(dto.phoneNumber() != null && memberRepository.existsByPhoneNumber(dto.phoneNumber()))
            throw new MemberException(EXIST_PHONE_NUMBER);

        Member foundMember = memberRepository.findByUsernameOrThrow(username);
        mapper.update(dto, foundMember);
        memberRepository.save(foundMember);
    }

    @Override
    public void withdraw(String username) {
        memberRepository.deleteByUsername(username);
    }
}

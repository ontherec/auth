package kr.ontherec.authorization.member.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import kr.ontherec.authorization.infra.UnitTest;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.exception.MemberException;
import kr.ontherec.authorization.member.exception.MemberExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@UnitTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("사용자 조회 성공")
    @Transactional(readOnly = true)
    void getExistUserByUsername() {
        // given
        String username = "test";

        // when
        Member findMember = memberService.getByUsername(username);

        // then
        assertThat(findMember.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("사용자 조회 실패 - 존재하지 않는 ID")
    @Transactional(readOnly = true)
    void getNotExistUserByUsername() {
        // given
        String username = "new";

        // when
        Throwable throwable = catchThrowable(() -> memberService.getByUsername(username));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerNewMember() {
        // given
        Member newMember = Member.builder()
                .username("new")
                .nickname("new-nickname")
                .phoneNumber("010-1234-5678")
                .build();

        // when
        memberService.register(newMember);

        // then
        assertThat(memberService.getByUsername(newMember.getUsername()).getUsername())
                .isEqualTo(newMember.getUsername());
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 존재하는 ID")
    void registerNewMemberWithExistUsername() {
        // given
        Member newMember = Member.builder()
                .username("test")
                .nickname("new-nickname")
                .phoneNumber("010-1234-5678")
                .build();

        // when
        Throwable throwable = catchThrowable(() -> memberService.register(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_USERNAME.getMessage());
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 닉네임 중복")
    void registerNewMemberWithDuplicatedNickname() {
        // given
        Member newMember = Member.builder()
                .username("new")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .build();

        // when
        Throwable throwable = catchThrowable(() -> memberService.register(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 전화번호 중복")
    void registerNewMemberWithDuplicatedPhoneNumber() {
        // given
        Member newMember = Member.builder()
                .username("new")
                .nickname("new-nickname")
                .phoneNumber("010-0000-0000")
                .build();

        // when
        Throwable throwable = catchThrowable(() -> memberService.register(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_PHONE_NUMBER.getMessage());
    }
}
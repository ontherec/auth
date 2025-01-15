package kr.ontherec.authorization.member.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.exception.MemberException;
import kr.ontherec.authorization.member.exception.MemberExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("ID로 사용자 조회 성공")
    void getExistUserByUsername() {
        // given
        String username = "test";

        // when
        Member findMember = memberService.getByUsername(username);

        // then
        assertThat(findMember.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 사용자 조회 실패")
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
    @DisplayName("새로운 사용자로 회원가입 성공")
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
    @DisplayName("중복된 닉네임으로 새로운 사용자 회원가입 실패")
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
    @DisplayName("중복된 전화번호로 새로운 사용자 회원가입 실패")
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
package kr.ontherec.authorization.member.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import kr.ontherec.authorization.infra.UnitTest;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import kr.ontherec.authorization.member.exception.MemberException;
import kr.ontherec.authorization.member.exception.MemberExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@UnitTest
class MemberServiceTest {
    private final MemberMapper memberMapper = MemberMapper.INSTANCE;

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
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "newMember",
                "newPassword",
                "newMember",
                "newMember",
                "010-0000-0001",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg"
        );
        Member newMember = memberMapper.signUpRequestDtoToEntity(dto);

        // when
        memberService.signUp(newMember);

        // then
        assertThat(memberService.getByUsername(newMember.getUsername()).getUsername())
                .isEqualTo(newMember.getUsername());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 ID")
    void registerNewMemberWithExistUsername() {
        // given
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "test",
                "newPassword",
                "newMember",
                "newMember",
                "010-0000-0001",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg"
        );
        Member newMember = memberMapper.signUpRequestDtoToEntity(dto);

        // when
        Throwable throwable = catchThrowable(() -> memberService.signUp(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_USERNAME.getMessage());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 닉네임")
    void registerNewMemberWithDuplicatedNickname() {
        // given
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "newMember",
                "newPassword",
                "newMember",
                "test",
                "010-0000-0001",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg"
        );
        Member newMember = memberMapper.signUpRequestDtoToEntity(dto);


        // when
        Throwable throwable = catchThrowable(() -> memberService.signUp(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 전화번호")
    void registerNewMemberWithDuplicatedPhoneNumber() {
        // given
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "newMember",
                "newPassword",
                "newMember",
                "newMember",
                "010-0000-0000",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg"
        );
        Member newMember = memberMapper.signUpRequestDtoToEntity(dto);


        // when
        Throwable throwable = catchThrowable(() -> memberService.signUp(newMember));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_PHONE_NUMBER.getMessage());
    }

    @Test
    @DisplayName("사용자 정보 수정 성공")
    void update() {
        // given
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "newPassword",
                "newMember",
                "newMember",
                "010-0000-0001",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/poster-1.png"
        );
        String username = "test";

        // when
        memberService.update(username, dto);
        Member foundMember = memberService.getByUsername(username);

        // then
        assertThat(foundMember.getName()).isEqualTo(dto.name());
        assertThat(foundMember.getNickname()).isEqualTo(dto.nickname());
        assertThat(foundMember.getPhoneNumber()).isEqualTo(dto.phoneNumber());
        assertThat(foundMember.getPicture()).isEqualTo(dto.picture());
    }

    @Test
    @DisplayName("사용자 정보 수정 실패 - 중복된 닉네임")
    void updateWithDuplicatedNickname() {
        // given
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "newPassword",
                "newMember",
                "test",
                "010-0000-0001",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/poster-1.png"
        );
        String username = "test";

        // when
        Throwable throwable = catchThrowable(() -> memberService.update(username, dto));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("사용자 정보 수정 실패 - 중복된 전화번호")
    void updateWithDuplicatedPhoneNumber() {
        // given
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "newPassword",
                "newMember",
                "newMember",
                "010-0000-0000",
                "https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/poster-1.png"
        );
        String username = "test";

        // when
        Throwable throwable = catchThrowable(() -> memberService.update(username, dto));

        // then
        assertThat(throwable)
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberExceptionCode.EXIST_PHONE_NUMBER.getMessage());
    }
}
package kr.ontherec.authorization.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import kr.ontherec.authorization.member.domain.Role;
import org.hibernate.validator.constraints.URL;

public record MemberSignUpDto (
        @NotBlank(message = "사용자 ID를 입력해주세요")
        @Size(max = 255, message = "사용자 ID는 최대 255글자 입니다")
        String username,
        @Size(max = 15, message = "비밀번호는 최대 15글자 입니다")
        String password,
        @Size(max = 15, message = "이름은 최대 15글자 입니다")
        String name,
        @NotBlank(message = "닉네임을 입력해주세요")
        @Size(max = 15, message = "닉네임은 최대 15글자 입니다")
        String nickname,
        @NotBlank(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message = "유효하지 않은 전화번호 입니다")
        String phoneNumber,
        @URL(message = "유효하지 않은 URL 입니다")
        String picture,
        Set<Role> roles
) {}

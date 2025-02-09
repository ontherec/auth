package kr.ontherec.authorization.member.dto;

import java.util.Set;
import kr.ontherec.authorization.member.domain.Authority;

public record MemberResponseDto(
        Long id,
        String username,
        String name,
        String nickname,
        String phoneNumber,
        String picture,
        Set<Authority> roles
) {}

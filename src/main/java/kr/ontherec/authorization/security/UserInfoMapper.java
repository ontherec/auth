package kr.ontherec.authorization.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.application.mapper.MemberMapper;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {
    private final MemberService memberService;
    private final MemberMapper mapper = MemberMapper.INSTANCE;
    private final ObjectMapper objectMapper;

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext context) {
        Member findMember = memberService.getByUsername(context.getAuthorization().getPrincipalName());
        MemberResponseDto responseDto = mapper.entityToResponseDto(findMember);
        Map<String, Object> claims = objectMapper.convertValue(responseDto, new TypeReference<>() {});
        return new OidcUserInfo(claims);
    }
}

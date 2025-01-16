package kr.ontherec.authorization.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {
    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext context) {
        Member findMember = memberService.getByUsername(context.getAuthorization().getPrincipalName());
        Map<String, Object> claims = objectMapper.convertValue(findMember, new TypeReference<>() {});
        claims.remove("password");
        return new OidcUserInfo(claims);
    }
}

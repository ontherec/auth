package kr.ontherec.authorization.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import kr.ontherec.authorization.domain.Member;
import kr.ontherec.authorization.service.MemberService;
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
        Member findMember = memberService.findByUsername(context.getAuthorization().getPrincipalName());
        Map<String, Object> claims = objectMapper.convertValue(findMember, new TypeReference<>() {});
        claims.remove("password");
        return new OidcUserInfo(claims);
    }
}

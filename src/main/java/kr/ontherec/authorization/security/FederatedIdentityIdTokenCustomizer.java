package kr.ontherec.authorization.security;

import java.util.Set;
import java.util.stream.Collectors;
import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class FederatedIdentityIdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void customize(JwtEncodingContext context) {
        logger.debug("\u001B[31m" + "issue {} token: {}" + "\u001B[0m", context.getTokenType().getValue(), context.getPrincipal().getName());

        if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
            Member findMember = memberService.getByUsername(context.getPrincipal().getName());
            Set<String> roles = findMember.getRoles().stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());

            context.getClaims().claims((claims) -> {
                claims.put("name", findMember.getName());
                claims.put("nickname", findMember.getNickname());
                claims.put("picture", findMember.getPicture());
                claims.put("roles", roles);
            });
        }
    }
}
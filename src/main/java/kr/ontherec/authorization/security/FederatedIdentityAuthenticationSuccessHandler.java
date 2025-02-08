package kr.ontherec.authorization.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import kr.ontherec.authorization.security.strategy.ClientRegistrationStrategy;
import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();
	private final MemberService memberService;
	private final List<ClientRegistrationStrategy> strategies;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (authentication instanceof OAuth2AuthenticationToken) {
			String clientRegistrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
			Map<String, Object> claims = oauth2User.getAttributes();

			logger.debug("social login: {}", clientRegistrationId);

			for (ClientRegistrationStrategy strategy : strategies) {
				if (strategy.isSupport(clientRegistrationId)) {
					Member newMember = strategy.parseClaimsToMember(claims);
					memberService.signUp(newMember);
				}
			}
		}

		this.delegate.onAuthenticationSuccess(request, response, authentication);
	}
}
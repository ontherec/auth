package kr.ontherec.authorization.config.strategy;

import java.util.List;
import java.util.Map;
import kr.ontherec.authorization.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientRegistrationContext {
    private final List<ClientRegistrationStrategy> strategies;

    public Member parseClaimsToMember(String clientRegistrationId, Map<String, Object> claims) {
        return getStrategy(clientRegistrationId).parseClaimsToMember(claims);
    }

    private ClientRegistrationStrategy getStrategy(String clientRegistrationId) {
        for (ClientRegistrationStrategy strategy : strategies) {
            if (strategy.isSupport(clientRegistrationId)) return strategy;
        }
        throw new RuntimeException("지원하지 않는 클라이언트 리포지토리 입니다.");
    }
}

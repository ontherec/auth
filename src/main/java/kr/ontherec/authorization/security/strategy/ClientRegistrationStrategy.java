package kr.ontherec.authorization.security.strategy;

import java.util.Map;
import kr.ontherec.authorization.member.domain.Member;

public interface ClientRegistrationStrategy {
    boolean isSupport(String clientRegistrationId);
    Member parseClaimsToMember(Map<String, Object> claims);
}

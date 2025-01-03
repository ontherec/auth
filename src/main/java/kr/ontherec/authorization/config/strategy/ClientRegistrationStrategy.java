package kr.ontherec.authorization.config.strategy;

import java.util.Map;
import kr.ontherec.authorization.entity.Member;

public interface ClientRegistrationStrategy {
    boolean isSupport(String clientRegistrationId);
    Member parseClaimsToMember(Map<String, Object> claims);
}

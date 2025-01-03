package kr.ontherec.authorization.config.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import kr.ontherec.authorization.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleStrategy implements ClientRegistrationStrategy{
    private static final String CLIENT_REGISTRATION_ID = "google";
    private final ObjectMapper objectMapper;

    @Override
    public boolean isSupport(String clientRegistrationId) {
        return CLIENT_REGISTRATION_ID.equals(clientRegistrationId);
    }

    @Override
    public Member parseClaimsToMember(Map<String, Object> claims) {
        return objectMapper.convertValue(claims, GoogleClaimsVo.class).toMember();
    }

    private record GoogleClaimsVo(
            String at_hash,
            String sub,
            boolean email_verified,
            String iss,
            String given_name,
            String nonce,
            String picture,
            List<String> aud,
            String azp,
            String name,
            String hd,
            LocalDateTime exp,
            String family_name,
            LocalDateTime iat,
            String email
    ) {
        private Member toMember() {
            return Member.builder()
                    .username(sub)
                    .imageUrl(picture)
                    .name(name)
                    .authorities(new HashSet<>())
                    .build();
        }
    }
}

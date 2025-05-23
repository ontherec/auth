package kr.ontherec.authorization.security.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Map;
import kr.ontherec.authorization.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoStrategy implements ClientRegistrationStrategy {
    private static final String CLIENT_REGISTRATION_ID = "kakao";
    private final ObjectMapper objectMapper;

    @Override
    public boolean isSupport(String clientRegistrationId) {
        return CLIENT_REGISTRATION_ID.equals(clientRegistrationId);
    }

    @Override
    public Member parseClaimsToMember(Map<String, Object> claims) {
        return objectMapper.convertValue(claims, KakaoClaimsVo.class).toMember();
    }

    private record KakaoClaimsVo(
            String id,
            LocalDateTime connected_at,
            Properties properties,
            KakaoAccount kakao_account
    ) {
        private record Properties(
                String nickname,
                String profile_image,
                String thumbnail_image
        ) {}

        private record KakaoAccount(
                boolean profile_nickname_needs_agreement,
                boolean profile_image_needs_agreement,
                Profile profile
        ) {}

        private record Profile(
                String nickname,
                String thumbnail_image_url,
                String profile_image_url,
                boolean is_default_image,
                boolean is_default_nickname
        ) {}

        private Member toMember() {
            return Member.builder()
                    .username(CLIENT_REGISTRATION_ID + "-" + id())
                    .nickname(CLIENT_REGISTRATION_ID + "-" + id())
                    .picture(properties().profile_image())
                    .build();
        }
    }
}


package kr.ontherec.authorization.config.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import kr.ontherec.authorization.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverStrategy implements ClientRegistrationStrategy{
    private static final String CLIENT_REGISTRATION_ID = "naver";
    private final ObjectMapper objectMapper;

    @Override
    public boolean isSupport(String clientRegistrationId) {
        return CLIENT_REGISTRATION_ID.equals(clientRegistrationId);
    }

    @Override
    public Member parseClaimsToMember(Map<String, Object> claims) {
        return objectMapper.convertValue(claims, NaverClaimsVo.class).toMember();
    }

    private record NaverClaimsVo(
            int resultcode,
            String message,
            Response response
    ) {
        private record Response(
                String id,
                String nickname,
                String profile_image,
                String mobile,
                String mobile_e164,
                String name
        ) {}

        private Member toMember() {
            return Member.builder()
                    .username(response.id)
                    .nickname(response.nickname)
                    .picture(response.profile_image)
                    .phoneNumber(response.mobile)
                    .name(response.name)
                    .build();
        }
    }
}

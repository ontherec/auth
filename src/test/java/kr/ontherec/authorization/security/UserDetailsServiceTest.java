package kr.ontherec.authorization.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import kr.ontherec.authorization.security.exception.SecurityException;
import kr.ontherec.authorization.security.exception.SecurityExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class UserDetailsServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("사용자 조회 성공")
    void getExistUserByUsername() {
        // given
        String username = "test";

        // when
        UserDetails findUser = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(findUser.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("사용자 조회 실패 - 존재하지 않는 ID")
    void getNotExistUserByUsername() {
        // given
        String username = "new";

        // when
        Throwable throwable = catchThrowable(() -> userDetailsService.loadUserByUsername(username));

        // then
        assertThat(throwable)
                .isInstanceOf(SecurityException.class)
                .hasMessage(SecurityExceptionCode.UNAUTHORIZED.getMessage());
    }
}
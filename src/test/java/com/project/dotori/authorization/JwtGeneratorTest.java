package com.project.dotori.authorization;

import com.project.dotori.JwtTestConfiguration;
import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.global.exception.AuthorizationException;
import com.project.dotori.member.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(JwtTestConfiguration.class)
@SpringBootTest
class JwtGeneratorTest {

    @Autowired
    private JwtGenerator jwtGenerator;

    @DisplayName("에세스 토큰을 생성한 뒤 토큰으로 멤버의 아이디를 가져온다.")
    @Test
    void generateToken() {
        // given
        var memberId = 1L;
        var role = Role.GOOGLE;

        // when
        var accessToken = jwtGenerator.generateAccessToken(memberId, role);
        var claims = jwtGenerator.getClaims(accessToken);

        // then
        assertThat(accessToken).isNotBlank();
        assertThat(claims).isEqualTo(memberId);
    }

    @DisplayName("올바르지 않은 토큰이면 예외가 발생한다.")
    @Test
    void invalidToken() {
        // given
        var token = "weired_token";

        // when & then
        assertThatThrownBy(() -> jwtGenerator.getClaims(token))
            .isInstanceOf(AuthorizationException.class);
    }

    @DisplayName("토큰이 비었으면 예외가 발생한다.")
    @ValueSource(strings = { "", " " })
    @ParameterizedTest
    void blankToken(
        String token
    ) {
        // when & then
        assertThatThrownBy(() -> jwtGenerator.getClaims(token))
            .isInstanceOf(AuthorizationException.class);
    }
}
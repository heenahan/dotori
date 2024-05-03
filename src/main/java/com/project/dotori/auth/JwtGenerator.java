package com.project.dotori.auth;

import com.project.dotori.global.exception.AuthorizationException;
import com.project.dotori.global.exception.ErrorCode;
import com.project.dotori.member.domain.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;

@Profile("!test")
@RequiredArgsConstructor
@Component
public class JwtGenerator {

    private static final String INVALID_TOKEN = "옳바르지 않은 토큰입니다. value = %s";
    private static final String BLANK_TOKEN = "토큰이 비었습니다. value = %s";

    private final JwtProperties jwtProperties;

    public String generateAccessToken(
        Long memberId,
        Role role
    ) {
        var now = new Date(System.currentTimeMillis());
        var accessTokenExpiresAt = new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiresAt());

        return Jwts.builder()
            .claims()
            .id(String.valueOf(memberId))
            .add("role", role)
            .and()
            .issuedAt(now)
            .expiration(accessTokenExpiresAt)
            .signWith(jwtProperties.getSecretKey())
            .compact();
    }

    public Long getClaims(
        String token
    ) {
        try {
            var claims = Jwts.parser()
                .verifyWith(jwtProperties.getSecretKey())
                .build()
                .parseSignedClaims(token);

            var id = claims.getPayload()
                        .getId();

            return Long.parseLong(id);
        } catch (JwtException e) {
            throw new AuthorizationException(ErrorCode.INVALID_TOKEN, INVALID_TOKEN.formatted(token));
        } catch (IllegalArgumentException e) {
            throw new AuthorizationException(ErrorCode.INVALID_TOKEN, BLANK_TOKEN.formatted(token));
        }
    }
}

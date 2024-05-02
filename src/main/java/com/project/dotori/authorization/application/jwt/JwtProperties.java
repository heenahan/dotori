package com.project.dotori.authorization.application.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import javax.crypto.SecretKey;

@Getter
@Setter
@AllArgsConstructor
@Profile("!test")
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {


    private Long accessTokenExpiresAt;
    private String secretKey;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }
}

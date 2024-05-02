package com.project.dotori.authorization.infrastructure.openfeign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2.google")
public class GoogleProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private Url url;

    @Getter
    @Setter
    @AllArgsConstructor
    static class Url {
        private String accessToken;
        private String userInfo;
    }

    public URI getAccessTokenUrl() {
        return URI.create(url.getAccessToken());
    }

    public URI getUserInfoUrl() {
        return URI.create(url.getUserInfo());
    }
}

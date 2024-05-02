package com.project.dotori.authorization.infrastructure.openfeign;

import com.project.dotori.authorization.application.Oauth2ApiService;
import com.project.dotori.authorization.application.request.Oauth2AccessTokenRequest;
import com.project.dotori.authorization.application.request.Oauth2CodeRequest;
import com.project.dotori.authorization.application.response.Oauth2AccessTokenResponse;
import com.project.dotori.authorization.application.response.Oauth2UserInfoResponse;
import com.project.dotori.authorization.infrastructure.openfeign.request.GoogleAccessTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleService implements Oauth2ApiService {

    private final GoogleProperties googleProperties;
    private final GoogleOpenFeign googleOpenFeign;

    @Override
    public Oauth2AccessTokenResponse getAccessToken(
        Oauth2CodeRequest request
    ) {
        var googleAccessTokenRequest = GoogleAccessTokenRequest.of(request, googleProperties.getClientId(), googleProperties.getClientSecret(), googleProperties.getRedirectUri());
        var accessTokenUrl = googleProperties.getAccessTokenUrl();
        var response = googleOpenFeign.getAccessToken(accessTokenUrl, googleAccessTokenRequest);

        return response.toService();
    }

    @Override
    public Oauth2UserInfoResponse getUserInfo(Oauth2AccessTokenRequest request) {
        var userInfoUrl = googleProperties.getUserInfoUrl();
        var userInfo = googleOpenFeign.getUserInfo(userInfoUrl, request.accessToken());

        return userInfo.toService();
    }
}

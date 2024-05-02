package com.project.dotori.authorization.application;

import com.project.dotori.authorization.application.request.Oauth2AccessTokenRequest;
import com.project.dotori.authorization.application.request.Oauth2CodeRequest;
import com.project.dotori.authorization.application.response.Oauth2AccessTokenResponse;
import com.project.dotori.authorization.application.response.Oauth2UserInfoResponse;

public interface Oauth2ApiService {

    Oauth2AccessTokenResponse getAccessToken(
        Oauth2CodeRequest request
    );

    Oauth2UserInfoResponse getUserInfo(
        Oauth2AccessTokenRequest request
    );
}

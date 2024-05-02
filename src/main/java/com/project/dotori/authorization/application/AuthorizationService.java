package com.project.dotori.authorization.application;

import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.authorization.application.request.GoogleCodeServiceRequest;
import com.project.dotori.authorization.application.request.Oauth2AccessTokenRequest;
import com.project.dotori.authorization.application.request.Oauth2CodeRequest;
import com.project.dotori.authorization.application.response.LoginResponse;
import com.project.dotori.member.application.MemberService;
import com.project.dotori.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService {

    private final Oauth2ApiService oauth2ApiService;
    private final MemberService memberService;
    private final JwtGenerator jwtGenerator;

    public LoginResponse login(
        GoogleCodeServiceRequest request
    ) {
        // access token 가져옴
        var oauth2CodeRequest = Oauth2CodeRequest.from(request);
        var oauth2AccessTokenResponse = oauth2ApiService.getAccessToken(oauth2CodeRequest);

        // 유저 정보 가져옴
        var oauth2AccessTokenRequest = Oauth2AccessTokenRequest.from(oauth2AccessTokenResponse);
        var userInfo = oauth2ApiService.getUserInfo(oauth2AccessTokenRequest);

        // 저장 후 access token 생성
        var memberId = memberService.createMember(userInfo.toService());
        var accessToken = jwtGenerator.generateAccessToken(memberId, Role.GOOGLE);

        return LoginResponse.from(accessToken);
    }
}

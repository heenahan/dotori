package com.project.dotori.authorization.infrastructure.openfeign;

import com.project.dotori.authorization.infrastructure.openfeign.request.GoogleAccessTokenRequest;
import com.project.dotori.authorization.infrastructure.openfeign.response.GoogleAccessTokenResponse;
import com.project.dotori.authorization.infrastructure.openfeign.response.GoogleUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "GoogleOpenFeign", url = "GOOGLE_API_URI")
public interface GoogleOpenFeign {

    @PostMapping
    GoogleAccessTokenResponse getAccessToken(
        URI uri,
        @RequestBody GoogleAccessTokenRequest request
    );

    @GetMapping
    GoogleUserInfoResponse getUserInfo(
        URI uri,
        @RequestParam("access_token") String accessToken
    );
}

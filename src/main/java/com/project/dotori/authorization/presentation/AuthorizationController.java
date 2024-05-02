package com.project.dotori.authorization.presentation;

import com.project.dotori.authorization.application.AuthorizationService;
import com.project.dotori.authorization.application.response.LoginResponse;
import com.project.dotori.authorization.presentation.request.GoogleCodeRequest;
import com.project.dotori.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @GetMapping("/login/google")
    public ResponseEntity<ApiResponse<LoginResponse>> loginGoogle(
        @ModelAttribute GoogleCodeRequest request
    ) {
        var response = authorizationService.login(request.toService());

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}

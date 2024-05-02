package com.project.dotori.authorization.presentation.request;

import com.project.dotori.authorization.application.request.GoogleCodeServiceRequest;

public record GoogleCodeRequest(
    String code
) {

    public GoogleCodeServiceRequest toService() {
        return GoogleCodeServiceRequest.builder()
            .code(code)
            .build();
    }
}

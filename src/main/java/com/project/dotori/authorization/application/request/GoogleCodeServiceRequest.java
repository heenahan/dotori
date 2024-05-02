package com.project.dotori.authorization.application.request;

import lombok.Builder;

@Builder
public record GoogleCodeServiceRequest(
    String code
) {
}

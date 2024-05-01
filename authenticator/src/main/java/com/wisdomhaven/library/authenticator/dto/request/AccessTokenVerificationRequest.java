package com.wisdomhaven.library.authenticator.dto.request;

import jakarta.validation.constraints.NotNull;

public record AccessTokenVerificationRequest(
        @NotNull
        String accessToken) {
}

package com.wisdomhaven.library.authenticator.dto.request;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(
        @NotNull
        String refreshToken) {
}

package com.wisdomhaven.library.authenticator.dto.request;

import jakarta.validation.constraints.NotNull;

public record TokenRequest(
        @NotNull
        String username,
        @NotNull
        String password
) {
}

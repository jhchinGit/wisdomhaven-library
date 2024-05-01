package com.wisdomhaven.library.authenticator.dto.response;

import lombok.Builder;

@Builder
public record AccessTokenVerificationResponseDTO(boolean isValid) {
}

package com.wisdomhaven.library.authenticator.dto.response;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {
}

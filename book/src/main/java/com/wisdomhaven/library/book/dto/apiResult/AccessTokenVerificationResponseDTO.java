package com.wisdomhaven.library.book.dto.apiResult;

import lombok.Builder;

@Builder
public record AccessTokenVerificationResponseDTO(boolean isValid) {
}

package com.wisdomhaven.library.book.dto.request;

import lombok.Builder;

@Builder
public record AccessTokenVerificationRequest(String accessToken) {
}

package com.wisdomhaven.library.borrowing.dto.request;

import lombok.Builder;

@Builder
public record AccessTokenVerificationRequest(String accessToken) {
}

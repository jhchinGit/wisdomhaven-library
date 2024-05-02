package com.wisdomhaven.library.borrower.dto.request;

import lombok.Builder;

@Builder
public record AccessTokenVerificationRequest(String accessToken) {
}

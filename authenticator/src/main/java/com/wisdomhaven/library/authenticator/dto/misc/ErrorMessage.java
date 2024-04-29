package com.wisdomhaven.library.authenticator.dto.misc;

import lombok.Builder;

@Builder
public record ErrorMessage(String propertyPath, String message) {}

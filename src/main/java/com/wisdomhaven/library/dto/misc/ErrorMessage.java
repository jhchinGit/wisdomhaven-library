package com.wisdomhaven.library.dto.misc;

import lombok.Builder;

@Builder
public record ErrorMessage(String propertyPath, String message) {}

package com.wisdomhaven.library.borrowing.dto.misc;

import lombok.Builder;

@Builder
public record ErrorMessage(String propertyPath, String message) {}

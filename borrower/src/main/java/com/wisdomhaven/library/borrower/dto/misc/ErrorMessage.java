package com.wisdomhaven.library.borrower.dto.misc;

import lombok.Builder;

@Builder
public record ErrorMessage(String propertyPath, String message) {}

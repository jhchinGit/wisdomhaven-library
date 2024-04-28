package com.wisdomhaven.library.book.dto.misc;

import lombok.Builder;

@Builder
public record ErrorMessage(String propertyPath, String message) {}

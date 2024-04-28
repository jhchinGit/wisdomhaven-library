package com.wisdomhaven.library.book.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookUpdateRequestBody(
        @NotNull
        Boolean isAvailable)
{}

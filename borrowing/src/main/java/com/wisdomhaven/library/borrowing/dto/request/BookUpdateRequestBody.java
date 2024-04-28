package com.wisdomhaven.library.borrowing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookUpdateRequestBody(
        @NotNull
        Boolean isAvailable)
{}

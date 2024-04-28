package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookUpdateRequestBody(
        @NotNull
        Boolean isAvailable)
{}

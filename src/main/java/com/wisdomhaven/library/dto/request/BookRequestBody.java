package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookRequestBody(
        @NotNull
        String title,
        @NotNull
        String author,
        @NotNull
        @Pattern(regexp = "^\\d{10}|\\d{13}$", message = "Invalid format")
        String isbn)
{}

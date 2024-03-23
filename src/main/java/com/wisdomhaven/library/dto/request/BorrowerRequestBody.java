package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record BorrowerRequestBody(
        @NotNull
        String name,
        @NotNull
        @Email(message = "Invalid Email format")
        String email)
{}

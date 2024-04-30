package com.wisdomhaven.library.authenticator.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestBody(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        String username,
        @NotNull
        @Size(min = 8, message = "The length must be 8 or more.")
        String password,
        @NotNull
        @Email(message = "Invalid Email format.")
        String email) {
}

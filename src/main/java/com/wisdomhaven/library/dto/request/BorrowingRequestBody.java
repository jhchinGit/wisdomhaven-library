package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record BorrowingRequestBody(
        @NotNull
        Integer borrowerId,
        @NotEmpty
        List<@NotNull @Pattern(regexp = "^\\d{10}|\\d{13}$", message = "Invalid format.") String> isbnList)
{}

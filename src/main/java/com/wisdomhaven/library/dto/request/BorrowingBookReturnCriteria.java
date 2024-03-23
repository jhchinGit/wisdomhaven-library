package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestParam;

public record BorrowingBookReturnCriteria(
        @RequestParam("transactionId")
        @NotNull
        Integer transactionId,

        @RequestParam("isbn")
        @NotNull
        @Pattern(regexp = "^\\d{10}|\\d{13}$", message = "Invalid format.")
        String isbn
) {}

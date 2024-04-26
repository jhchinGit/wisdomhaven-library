package com.wisdomhaven.library.borrowing.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BorrowingRequestBody(
        @NotNull
        Integer borrowerId,
        @NotEmpty
        List<@NotNull Integer> bookIdList)
{}

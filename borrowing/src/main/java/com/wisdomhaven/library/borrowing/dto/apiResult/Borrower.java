package com.wisdomhaven.library.borrowing.dto.apiResult;

import lombok.Builder;

@Builder
public record Borrower(Integer borrowerId, String name, String email) {}

package com.wisdomhaven.library.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

public record BorrowingAllBooksReturnCriteria(
        @RequestParam("transactionId")
        Integer transactionId) {}

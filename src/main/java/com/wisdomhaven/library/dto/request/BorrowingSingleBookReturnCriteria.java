package com.wisdomhaven.library.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

public record BorrowingSingleBookReturnCriteria(
        @RequestParam("transactionId")
        Integer transactionId,

        @RequestParam("bookId")
        Integer bookId
) {}

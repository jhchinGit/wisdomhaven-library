package com.wisdomhaven.library.borrowing.dto.apiResult;

import lombok.Builder;

@Builder
public record Book(
        Integer bookId,
        String title,
        String author,
        String isbn,
        Boolean isAvailable) {}

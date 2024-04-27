package com.wisdomhaven.library.borrowing.dto.response;

import lombok.Builder;

@Builder
public record BorrowingDetailResponseDTO(
        Integer borrowingDetailId,
        Integer bookId,
        String title,
        String isbn) {
}

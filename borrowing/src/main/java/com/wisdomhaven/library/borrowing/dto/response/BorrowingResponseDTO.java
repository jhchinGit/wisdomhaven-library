package com.wisdomhaven.library.borrowing.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BorrowingResponseDTO(
        Integer borrowingId,
        Integer borrowerId,
        String borrowerName,
        List<BorrowingDetailResponseDTO> details) {}

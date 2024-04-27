package com.wisdomhaven.library.borrowing.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BorrowingResponseDTO(Integer borrowingId, List<BorrowingDetailResponseDTO> details) {}

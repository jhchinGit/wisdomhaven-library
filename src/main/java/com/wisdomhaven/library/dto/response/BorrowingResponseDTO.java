package com.wisdomhaven.library.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BorrowingResponseDTO(Integer transactionId, List<BookResponseDTO> bookResponseDTOList) {}

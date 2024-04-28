package com.wisdomhaven.library.borrower.dto.response;

import lombok.Builder;

@Builder
public record BorrowerResponseDTO(Integer borrowerId, String name, String email) {}

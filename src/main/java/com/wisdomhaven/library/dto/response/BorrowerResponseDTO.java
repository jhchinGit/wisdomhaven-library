package com.wisdomhaven.library.dto.response;

import lombok.Builder;

@Builder
public record BorrowerResponseDTO(Integer borrowerId, String name, String email) {}

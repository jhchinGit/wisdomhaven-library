package com.wisdomhaven.library.dto.response;

import lombok.Builder;

@Builder
public record BorrowerResponseDTO(Integer id, String name, String email) {}

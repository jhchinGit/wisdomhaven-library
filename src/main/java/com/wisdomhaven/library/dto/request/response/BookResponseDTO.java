package com.wisdomhaven.library.dto.request.response;

import lombok.Builder;

@Builder
public record BookResponseDTO(Integer id, String title, String author, String isbn) {}

package com.wisdomhaven.library.dto.response;

import lombok.Builder;

@Builder
public record BookResponseDTO(Integer bookId, String title, String author, String isbn) {}

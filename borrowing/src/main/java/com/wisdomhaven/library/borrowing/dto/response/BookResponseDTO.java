package com.wisdomhaven.library.borrowing.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record BookResponseDTO(
        Integer bookId,
        String title,
        String author,
        String isbn,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Boolean isAvailable) {}

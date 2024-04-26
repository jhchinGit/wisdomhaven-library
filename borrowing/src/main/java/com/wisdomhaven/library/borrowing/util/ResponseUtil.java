package com.wisdomhaven.library.borrowing.util;

import com.wisdomhaven.library.borrowing.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public final class ResponseUtil {
    private ResponseUtil() {}

    public static ResponseEntity buildResponseEntity(HttpStatusCode status) {
        return buildResponseEntity(null, status);
    }

    public static <T> ResponseEntity buildResponseEntity(@Nullable T body, HttpStatusCode status) {
        if (body instanceof Page<?> page) {
            return new ResponseEntity<>(buildPageResponse(page), status);
        } else {
            return new ResponseEntity<>(body, status);
        }
    }

    private static <T> PageResponseDTO<T> buildPageResponse(Page<T> page) {
        int pageNumber = page.getPageable().getPageNumber() + 1;
        return new PageResponseDTO<>(
                page.getTotalElements(),
                page.getContent().size(),
                pageNumber,
                page.getPageable().getPageSize(),
                page.getTotalElements() - (long) pageNumber *  page.getPageable().getPageSize() > 0,
                page.getContent());
    }
}

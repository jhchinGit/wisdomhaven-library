package com.wisdomhaven.library.borrowing.dto.response;

import java.util.List;

public record PageResponseDTO<T>(
        long total,
        int count,
        int pageNumber,
        int pageSize,
        boolean hasMore,
        List<T> items
) {}

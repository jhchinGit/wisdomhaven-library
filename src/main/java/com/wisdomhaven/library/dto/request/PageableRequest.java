package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record PageableRequest(
        @RequestParam("pageNumber")
        Optional<@Min(value = 1, message = "Page number starts from 1") Integer> pageNumber,
        @RequestParam("pageSize")
        Optional<Integer> pageSize
) {}

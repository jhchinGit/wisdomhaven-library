package com.wisdomhaven.library.dto.request;

import com.wisdomhaven.library.constant.ErrorMessageConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record PageableRequest(
        @RequestParam("pageNumber")
        Optional<@Min(value = 1, message = "Page number starts from 1") Integer> pageNumber,
        @RequestParam("pageSize")
        Optional<Integer> pageSize,
        @RequestParam("orderBy")
        Optional<@Pattern(
                regexp = "[a-zA-Z\\d]+:(?i)(asc|desc)(?:,[a-zA-Z\\d]+:(?i)(asc|desc))*",
                message = ErrorMessageConstants.INVALID_ORDER_BY) String> orderBy
) {}

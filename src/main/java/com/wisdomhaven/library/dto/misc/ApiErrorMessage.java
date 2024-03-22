package com.wisdomhaven.library.dto.misc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record ApiErrorMessage(
        String title,
        int statusCode,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ErrorMessage> errorMessages) {}

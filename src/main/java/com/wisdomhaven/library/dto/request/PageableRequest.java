package com.wisdomhaven.library.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record PageableRequest(
        @RequestParam("limit")
        Optional<Integer> limit,
        @RequestParam("offset")
        Optional<Integer> offset
) {}

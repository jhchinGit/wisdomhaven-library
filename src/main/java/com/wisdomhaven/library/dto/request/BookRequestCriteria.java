package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record BookRequestCriteria(
        @RequestParam("id")
        Optional<Integer> id,
        @RequestParam("title")
        Optional<String> title,
        @RequestParam("author")
        Optional<String> author,
        @RequestParam("isbn")
        Optional<@Pattern(regexp = "^\\d{10}|\\d{13}$", message = "Invalid format") String> isbn
) {}
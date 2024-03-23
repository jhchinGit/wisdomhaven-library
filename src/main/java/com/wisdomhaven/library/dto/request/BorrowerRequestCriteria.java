package com.wisdomhaven.library.dto.request;

import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record BorrowerRequestCriteria(
        @RequestParam("id")
        Optional<Integer> id,
        @RequestParam("name")
        Optional<String> name,
        @RequestParam("email")
        Optional<@Email(message = "Invalid Email format.") String> email
) {}
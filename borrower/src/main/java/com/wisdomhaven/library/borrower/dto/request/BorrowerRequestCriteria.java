package com.wisdomhaven.library.borrower.dto.request;

import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public record BorrowerRequestCriteria(
        @RequestParam("borrowerId")
        Optional<Integer> borrowerId,
        @RequestParam("name")
        Optional<String> name,
        @RequestParam("email")
        Optional<@Email(message = "Invalid Email format.") String> email
) {}
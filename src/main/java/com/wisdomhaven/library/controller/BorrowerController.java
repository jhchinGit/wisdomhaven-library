package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BorrowerRequestBody;
import com.wisdomhaven.library.dto.request.BorrowerRequestCriteria;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.service.impl.BorrowerService;
import com.wisdomhaven.library.util.responseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path="borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowerResponseDTO>> getBorrowers(BorrowerRequestCriteria requestCriteria) {
        responseUtil.validateRequest(requestCriteria);

        List<BorrowerResponseDTO> borrowerResponseDTOList = this.borrowerService
                .getBorrowers(requestCriteria.id().orElse(null),
                        requestCriteria.name().orElse(null),
                        requestCriteria.email().orElse(null));
        return new ResponseEntity<>(borrowerResponseDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BorrowerResponseDTO> createBorrower(@RequestBody BorrowerRequestBody borrowerRequestBody) {
        responseUtil.validateRequest(borrowerRequestBody);

        return new ResponseEntity<>(
                this.borrowerService.createBorrower(
                        borrowerRequestBody.name(),
                        borrowerRequestBody.email()),
                HttpStatus.CREATED);
    }
}

package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BorrowerRequestBody;
import com.wisdomhaven.library.dto.request.BorrowerRequestCriteria;
import com.wisdomhaven.library.dto.request.PageableRequest;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.service.IBorrowerService;
import com.wisdomhaven.library.util.PageableUtil;
import com.wisdomhaven.library.util.RequestUtil;
import com.wisdomhaven.library.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "borrowers")
public class BorrowerController {

    private final IBorrowerService borrowerService;

    @Autowired
    public BorrowerController(IBorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity getBorrowers(BorrowerRequestCriteria requestCriteria,
                                       PageableRequest pageableRequest) {
        RequestUtil.validate(requestCriteria, pageableRequest);

        Page<BorrowerResponseDTO> borrowerResponseDTOList = this.borrowerService
                .getBorrowers(requestCriteria.id().orElse(null),
                        requestCriteria.name().orElse(null),
                        requestCriteria.email().orElse(null),
                        PageableUtil.getPageNumber(pageableRequest.pageNumber().orElse(null)),
                        pageableRequest.pageSize().orElse(10),
                        pageableRequest.orderBy().orElse(null));
        return ResponseUtil.buildResponseEntity(borrowerResponseDTOList, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createBorrower(@RequestBody BorrowerRequestBody borrowerRequestBody) {
        RequestUtil.validate(borrowerRequestBody);

        return ResponseUtil.buildResponseEntity(
                this.borrowerService.createBorrower(
                        borrowerRequestBody.name(),
                        borrowerRequestBody.email()),
                HttpStatus.CREATED);
    }
}

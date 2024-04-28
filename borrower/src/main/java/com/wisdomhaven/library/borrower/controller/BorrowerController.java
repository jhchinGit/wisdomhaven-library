package com.wisdomhaven.library.borrower.controller;

import com.wisdomhaven.library.borrower.dto.request.BorrowerRequestBody;
import com.wisdomhaven.library.borrower.dto.request.BorrowerRequestCriteria;
import com.wisdomhaven.library.borrower.dto.request.PageableRequest;
import com.wisdomhaven.library.borrower.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.borrower.service.IBorrowerService;
import com.wisdomhaven.library.borrower.util.PageableUtil;
import com.wisdomhaven.library.borrower.util.RequestUtil;
import com.wisdomhaven.library.borrower.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
                .getBorrowers(requestCriteria.borrowerId().orElse(null),
                        requestCriteria.name().orElse(null),
                        requestCriteria.email().orElse(null),
                        PageableUtil.getPageNumber(pageableRequest.pageNumber().orElse(null)),
                        pageableRequest.pageSize().orElse(10),
                        pageableRequest.orderBy().orElse(null));
        return ResponseUtil.buildResponseEntity(borrowerResponseDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/{borrowerId}", produces = "application/json")
    public ResponseEntity getBorrower(@PathVariable("borrowerId") Integer borrowerId) {
        return ResponseUtil.buildResponseEntity(this.borrowerService.getBorrower(borrowerId), HttpStatus.OK);
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

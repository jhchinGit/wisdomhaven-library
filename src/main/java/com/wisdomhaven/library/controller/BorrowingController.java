package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BorrowingBookReturnCriteria;
import com.wisdomhaven.library.dto.request.BorrowingRequestBody;
import com.wisdomhaven.library.service.IBorrowingService;
import com.wisdomhaven.library.util.RequestUtil;
import com.wisdomhaven.library.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "borrowings")
public class BorrowingController {

    private final IBorrowingService borrowingService;

    @Autowired
    public BorrowingController(IBorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createBorrowing(@RequestBody BorrowingRequestBody borrowingRequestBody) {
        RequestUtil.validate(borrowingRequestBody);

        return ResponseUtil.buildResponseEntity(
                this.borrowingService.createBorrowing(
                        borrowingRequestBody.borrowerId(),
                        borrowingRequestBody.isbnList()),
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{transactionId}/books/{isbn}")
    public ResponseEntity returnBookByIsbn(BorrowingBookReturnCriteria borrowingBookReturnCriteria) {
        this.borrowingService.returnBookByIsbn(borrowingBookReturnCriteria.transactionId(), borrowingBookReturnCriteria.isbn());
        return ResponseUtil.buildResponseEntity(HttpStatus.OK);
    }
}

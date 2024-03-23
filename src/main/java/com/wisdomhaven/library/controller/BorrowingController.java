package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BorrowingAllBooksReturnCriteria;
import com.wisdomhaven.library.dto.request.BorrowingSingleBookReturnCriteria;
import com.wisdomhaven.library.dto.request.BorrowingRequestBody;
import com.wisdomhaven.library.service.IBorrowingService;
import com.wisdomhaven.library.util.RequestUtil;
import com.wisdomhaven.library.util.ResponseUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
                        borrowingRequestBody.bookIdList()),
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity returnAllBooks(BorrowingAllBooksReturnCriteria borrowingAllBooksReturnCriteria) {
        this.borrowingService.returnAllBooks(borrowingAllBooksReturnCriteria.transactionId());
        return ResponseUtil.buildResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{transactionId}/books/{bookId}")
    public ResponseEntity returnBookByBookId(BorrowingSingleBookReturnCriteria borrowingSingleBookReturnCriteria) {
        this.borrowingService.returnBookByBookId(borrowingSingleBookReturnCriteria.transactionId(), borrowingSingleBookReturnCriteria.bookId());
        return ResponseUtil.buildResponseEntity(HttpStatus.OK);
    }
}

package com.wisdomhaven.library.borrowing.controller;

import com.wisdomhaven.library.borrowing.dto.request.BorrowingRequestBody;
import com.wisdomhaven.library.borrowing.service.IBorrowingService;
import com.wisdomhaven.library.borrowing.util.RequestUtil;
import com.wisdomhaven.library.borrowing.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity createBorrowing(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                          @RequestBody BorrowingRequestBody borrowingRequestBody) {
        RequestUtil.validate(borrowingRequestBody);

        return ResponseUtil.buildResponseEntity(
                this.borrowingService.createBorrowing(
                        accessToken,
                        borrowingRequestBody.borrowerId(),
                        borrowingRequestBody.bookIdList()),
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{borrowingId}")
    public ResponseEntity returnAllBooks(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                         @PathVariable("borrowingId") Integer borrowingId) {
        return ResponseUtil.buildResponseEntity(this.borrowingService.returnAllBooks(accessToken, borrowingId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{borrowingId}/books/{bookId}")
    public ResponseEntity returnBookByBookId(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                             @PathVariable("borrowingId") Integer borrowingId,
                                             @PathVariable("bookId") Integer bookId) {
        return ResponseUtil.buildResponseEntity(this.borrowingService.returnBookByBookId(accessToken, borrowingId, bookId), HttpStatus.OK);
    }
}

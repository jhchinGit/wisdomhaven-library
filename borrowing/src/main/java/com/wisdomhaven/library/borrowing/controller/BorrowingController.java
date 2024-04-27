package com.wisdomhaven.library.borrowing.controller;

import com.wisdomhaven.library.borrowing.dto.request.BorrowingRequestBody;
import com.wisdomhaven.library.borrowing.service.IBorrowingService;
import com.wisdomhaven.library.borrowing.util.RequestUtil;
import com.wisdomhaven.library.borrowing.util.ResponseUtil;
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
                        borrowingRequestBody.bookIdList()),
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{borrowingId}")
    public ResponseEntity returnAllBooks(@PathVariable("borrowingId") Integer borrowingId) {
        return ResponseUtil.buildResponseEntity(this.borrowingService.returnAllBooks(borrowingId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{borrowingId}/books/{bookId}")
    public ResponseEntity returnBookByBookId(@PathVariable("borrowingId") Integer borrowingId,
                                             @PathVariable("bookId") Integer bookId) {
        return ResponseUtil.buildResponseEntity(this.borrowingService.returnBookByBookId(borrowingId, bookId), HttpStatus.OK);
    }
}

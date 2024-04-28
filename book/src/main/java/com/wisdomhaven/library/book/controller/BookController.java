package com.wisdomhaven.library.book.controller;

import com.wisdomhaven.library.book.dto.request.BookRequestBody;
import com.wisdomhaven.library.book.dto.request.BookRequestCriteria;
import com.wisdomhaven.library.book.dto.request.BookUpdateRequestBody;
import com.wisdomhaven.library.book.dto.request.PageableRequest;
import com.wisdomhaven.library.book.dto.response.BookResponseDTO;
import com.wisdomhaven.library.book.service.IBookService;
import com.wisdomhaven.library.book.util.PageableUtil;
import com.wisdomhaven.library.book.util.RequestUtil;
import com.wisdomhaven.library.book.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="books")
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity getBooks(BookRequestCriteria requestCriteria,
                                   PageableRequest pageableRequest) {
        RequestUtil.validate(requestCriteria, pageableRequest);

        Page<BookResponseDTO> bookResponseDTOList = this.bookService
                .getBooks(requestCriteria.bookId().orElse(null),
                        requestCriteria.title().orElse(null),
                        requestCriteria.author().orElse(null),
                        requestCriteria.isbn().orElse(null),
                        PageableUtil.getPageNumber(pageableRequest.pageNumber().orElse(null)),
                        pageableRequest.pageSize().orElse(10),
                        pageableRequest.orderBy().orElse(null));
        return ResponseUtil.buildResponseEntity(bookResponseDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/{bookId}", produces = "application/json")
    public ResponseEntity getBook(@PathVariable("bookId") Integer bookId) {
        return ResponseUtil.buildResponseEntity(this.bookService.getBook(bookId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createBook(@RequestBody BookRequestBody bookRequestBody) {
        RequestUtil.validate(bookRequestBody);

        return ResponseUtil.buildResponseEntity(
                this.bookService.createBook(
                        bookRequestBody.title(),
                        bookRequestBody.author(),
                        bookRequestBody.isbn()),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{bookId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateBookAvailability(@PathVariable("bookId") Integer bookId, @RequestBody BookUpdateRequestBody bookUpdateRequestBody) {
        RequestUtil.validate(bookUpdateRequestBody);

        return ResponseUtil.buildResponseEntity(
                this.bookService.updateBookAvailability(
                        bookId,
                        bookUpdateRequestBody.isAvailable()),
                HttpStatus.OK);
    }
}
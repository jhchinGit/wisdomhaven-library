package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BookRequestBody;
import com.wisdomhaven.library.dto.request.BookRequestCriteria;
import com.wisdomhaven.library.dto.request.PageableRequest;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.service.IBookService;
import com.wisdomhaven.library.util.PageableUtil;
import com.wisdomhaven.library.util.RequestUtil;
import com.wisdomhaven.library.util.ResponseUtil;
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
    public ResponseEntity getBook(@PathVariable Integer bookId) {
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

    // TODO: PUT AND DELETE
}
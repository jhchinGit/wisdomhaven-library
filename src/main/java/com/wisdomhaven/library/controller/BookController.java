package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BookRequestBody;
import com.wisdomhaven.library.dto.request.BookRequestCriteria;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.service.IBookService;
import com.wisdomhaven.library.util.responseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getBooks(BookRequestCriteria requestCriteria) {
        responseUtil.validateRequest(requestCriteria);

        List<BookResponseDTO> bookResponseDTOList = bookService
                .getBooks(requestCriteria.id().orElse(null),
                        requestCriteria.title().orElse(null),
                        requestCriteria.author().orElse(null),
                        requestCriteria.isbn().orElse(null));
        return new ResponseEntity<>(bookResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Integer id) {
        BookResponseDTO bookResponseDTO = bookService.getBook(id);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestBody bookRequestBody) {
        responseUtil.validateRequest(bookRequestBody);

        return new ResponseEntity<>(
                bookService.createBook(
                        bookRequestBody.title(),
                        bookRequestBody.author(),
                        bookRequestBody.isbn()),
                HttpStatus.CREATED);
    }
}
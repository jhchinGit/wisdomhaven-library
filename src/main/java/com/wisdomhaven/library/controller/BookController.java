package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BookRequestCriteria;
import com.wisdomhaven.library.dto.request.response.BookResponseDTO;
import com.wisdomhaven.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getBooks(@Validated BookRequestCriteria requestCriteria) {
        List<BookResponseDTO> bookResponseDTOList = bookService
                .getBooks(requestCriteria.id().orElse(null),
                        requestCriteria.title().orElse(null),
                        requestCriteria.author().orElse(null),
                        requestCriteria.isbn().orElse(null));
        return new ResponseEntity<>(bookResponseDTOList, bookResponseDTOList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    // TODO: An endpoint which will only retrieve one book

//    @PostMapping
//    public ResponseEntity<Book> createBook(@RequestBody BookRequestBody bookRequestBody) {
//        return new ResponseEntity<>(
//                bookService.save(Book
//                        .builder()
//                                .title(bookRequestBody.title())
//                                .author(bookRequestBody.author())
//                                .isbn(bookRequestBody.isbn())
//                        .build()),
//                HttpStatus.CREATED);
//    }
}
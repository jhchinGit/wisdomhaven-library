package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BookRequestBody;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path="/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(
                StreamSupport.stream(
                        bookRepository.findAll().spliterator(),
                        false)
                        .toList(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequestBody bookRequestBody) {
        return new ResponseEntity<>(
                bookRepository.save(Book
                        .builder()
                                .title(bookRequestBody.title())
                                .author(bookRequestBody.author())
                                .isbn(bookRequestBody.isbn())
                        .build()),
                HttpStatus.CREATED);
    }
}
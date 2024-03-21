package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BookConverter;
import com.wisdomhaven.library.dto.request.response.BookResponseDTO;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookResponseDTO> getBooks(Integer id, String title, String author, String isbn) {
        return bookRepository
                .findByIdOrTitleOrAuthorOrIsbn(id, title, author, isbn)
                .stream()
                .map(BookConverter::ToBookResponseDTO)
                .toList();
    }
}

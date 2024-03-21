package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BookConverter;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookResponseDTO> getBooks(Integer id, String title, String author, String isbn) {
        List<BookResponseDTO> bookResponseDTOList = bookRepository
                .findByIdOrTitleOrAuthorOrIsbn(id, title, author, isbn)
                .stream()
                .map(BookConverter::ToBookResponseDTO)
                .toList();

        if (bookResponseDTOList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return bookResponseDTOList;
    }

    @Override
    public BookResponseDTO getBook(Integer id) {
        return bookRepository
                .findById(id)
                .map(BookConverter::ToBookResponseDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookResponseDTO createBook(String title, String author, String isbn) {
        return BookConverter.ToBookResponseDTO(
                        bookRepository.save(
                                Book.builder()
                                        .title(title)
                                        .author(author)
                                        .isbn(isbn)
                                        .build()));
    }
}

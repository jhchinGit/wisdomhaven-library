package com.wisdomhaven.library.book.converter;

import com.wisdomhaven.library.book.dto.response.BookResponseDTO;
import com.wisdomhaven.library.book.model.Book;

public class BookConverter {
    private BookConverter() {}
    public static BookResponseDTO toBookResponseDTO(Book book) {
        return BookResponseDTO
                .builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .isAvailable(book.isAvailable())
                .build();
    }
}

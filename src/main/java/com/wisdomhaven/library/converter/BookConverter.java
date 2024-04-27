package com.wisdomhaven.library.converter;

import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;

public class BookConverter {
    private BookConverter() {}
    public static BookResponseDTO toBookResponseDTO(Book book) {
        return BookResponseDTO
                .builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                // TODO: Create transaction service to check availability
                .isAvailable(true)
                .build();
    }
}

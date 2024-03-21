package com.wisdomhaven.library.converter;

import com.wisdomhaven.library.dto.request.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;

public class BookConverter {
    public static BookResponseDTO ToBookResponseDTO(Book book) {
        return BookResponseDTO
                .builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}

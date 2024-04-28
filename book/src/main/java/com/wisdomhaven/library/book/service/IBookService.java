package com.wisdomhaven.library.book.service;

import com.wisdomhaven.library.book.dto.response.BookResponseDTO;
import org.springframework.data.domain.Page;

public interface IBookService {
    Page<BookResponseDTO> getBooks(Integer bookId,
                                   String title,
                                   String author,
                                   String isbn,
                                   Integer pageNumber,
                                   Integer pageSize,
                                   String orderBy);
    BookResponseDTO getBook(Integer bookId);
    BookResponseDTO createBook(String title, String author, String isbn);
    BookResponseDTO updateBookAvailability(Integer bookId, Boolean isAvailable);
}

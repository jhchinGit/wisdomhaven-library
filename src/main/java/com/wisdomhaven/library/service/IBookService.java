package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BookResponseDTO;
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
}

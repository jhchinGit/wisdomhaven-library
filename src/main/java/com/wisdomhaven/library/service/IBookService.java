package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BookResponseDTO;
import org.springframework.data.domain.Page;

public interface IBookService {
    Page<BookResponseDTO> getBooks(
            Integer id,
            String title,
            String author,
            String isbn,
            Integer limit,
            Integer offset);
    BookResponseDTO getBook(Integer id);
    BookResponseDTO createBook(String title, String author, String isbn);
}

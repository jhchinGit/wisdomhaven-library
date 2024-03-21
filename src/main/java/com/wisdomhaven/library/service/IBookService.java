package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.request.response.BookResponseDTO;

import java.util.List;

public interface IBookService {
    List<BookResponseDTO> getBooks(Integer Id, String title, String author, String isbn);
}

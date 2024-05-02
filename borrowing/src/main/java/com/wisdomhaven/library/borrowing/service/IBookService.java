package com.wisdomhaven.library.borrowing.service;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;

import java.util.List;

public interface IBookService {
    List<Book> getListOfBooksByIds(String accessToken, List<Integer> bookIds);
    Book updateBookAvailability(String accessToken, Integer bookId, boolean isAvailable);
}

package com.wisdomhaven.library.borrowing.service;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;

import java.util.List;

public interface IBookService {
    List<Book> getListOfBooksByIds(List<Integer> bookIds);
}

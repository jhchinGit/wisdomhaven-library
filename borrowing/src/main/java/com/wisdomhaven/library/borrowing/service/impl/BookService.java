package com.wisdomhaven.library.borrowing.service.impl;

import com.wisdomhaven.library.borrowing.client.BookClient;
import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.service.IBookService;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements IBookService {
    private final BookClient bookClient;

    public BookService(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @Override
    public List<Book> getListOfBooksByIds(List<Integer> bookIds) {
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }

        return bookIds
                .stream()
                .map(this.bookClient::getBook)
                .map(HttpEntity::getBody)
                .toList();
    }
}

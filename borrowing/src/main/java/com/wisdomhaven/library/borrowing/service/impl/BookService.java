package com.wisdomhaven.library.borrowing.service.impl;

import com.wisdomhaven.library.borrowing.client.IBookClient;
import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.request.BookUpdateRequestBody;
import com.wisdomhaven.library.borrowing.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements IBookService {
    private final IBookClient bookClient;

    @Autowired
    public BookService(IBookClient bookClient) {
        this.bookClient = bookClient;
    }

    @Override
    public List<Book> getListOfBooksByIds(List<Integer> bookIds) {
        if (bookIds.isEmpty()) {
            return new ArrayList<>();
        }

        return bookIds
                .stream()
                .map(bookId -> {
                    ResponseEntity<Book> bookResponseEntity = this.bookClient.getBook(bookId);

                    if (bookResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return bookResponseEntity.getBody();
                    }

                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("Book Id %d not found.", bookId));
                })
                .toList();
    }

    @Override
    public Book updateBookAvailability(Integer bookId, boolean isAvailable) {
        ResponseEntity<Book> bookResponseEntity = this.bookClient.updateBookAvailability(bookId,
                BookUpdateRequestBody
                        .builder()
                        .isAvailable(isAvailable)
                        .build());

        if (bookResponseEntity.getStatusCode().is2xxSuccessful()) {
            return bookResponseEntity.getBody();
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Book Id %d availability update failed.", bookId));
    }
}

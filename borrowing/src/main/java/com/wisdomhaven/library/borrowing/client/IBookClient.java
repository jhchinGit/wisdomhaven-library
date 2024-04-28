package com.wisdomhaven.library.borrowing.client;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.request.BookUpdateRequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="bookClient", url = "localhost:8090/books")
public interface IBookClient {
    @GetMapping(value = "/{bookId}", produces = "application/json")
    ResponseEntity<Book> getBook(@PathVariable("bookId") Integer bookId);

    @PutMapping(value = "/{bookId}", produces = "application/json", consumes = "application/json")
    ResponseEntity<Book> updateBookAvailability(@PathVariable("bookId") Integer bookId,
                                                @RequestBody BookUpdateRequestBody bookUpdateRequestBody);
}

package com.wisdomhaven.library.borrowing.client;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.request.BookUpdateRequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="bookClient", url = "localhost:8090/books")
public interface IBookClient {
    @GetMapping(value = "/{bookId}", produces = "application/json")
    ResponseEntity<Book> getBook(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                 @PathVariable("bookId") Integer bookId);

    @PutMapping(value = "/{bookId}", produces = "application/json", consumes = "application/json")
    ResponseEntity<Book> updateBookAvailability(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                                @PathVariable("bookId") Integer bookId,
                                                @RequestBody BookUpdateRequestBody bookUpdateRequestBody);
}

package com.wisdomhaven.library.borrowing.client;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="bookClient", url = "localhost:8090/books")
public interface IBookClient {
    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}", produces = "application/json")
    ResponseEntity<Book> getBook(@PathVariable("bookId") Integer bookId);
}

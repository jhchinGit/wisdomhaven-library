package com.wisdomhaven.library.borrowing.client;

import com.wisdomhaven.library.borrowing.dto.apiResult.Borrower;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="borrowerClient", url = "localhost:8091/borrowers")
public interface IBorrowerClient {
    @GetMapping(value = "/{borrowerId}", produces = "application/json")
    ResponseEntity<Borrower> getBorrower(@PathVariable("borrowerId") Integer borrowerId);
}

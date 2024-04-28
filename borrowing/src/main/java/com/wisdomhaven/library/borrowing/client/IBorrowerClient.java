package com.wisdomhaven.library.borrowing.client;

import com.wisdomhaven.library.borrowing.dto.apiResult.Borrower;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="borrowerClient", url = "localhost:8091/borrowers")
public interface IBorrowerClient {
    @RequestMapping(method = RequestMethod.GET, value = "/{borrowerId}", produces = "application/json")
    ResponseEntity<Borrower> getBorrower(@PathVariable("borrowerId") Integer borrowerId);
}

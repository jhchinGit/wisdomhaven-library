package com.wisdomhaven.library.borrowing.service.impl;

import com.wisdomhaven.library.borrowing.client.IBorrowerClient;
import com.wisdomhaven.library.borrowing.dto.apiResult.Borrower;
import com.wisdomhaven.library.borrowing.service.IBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BorrowerService implements IBorrowerService {
    private final IBorrowerClient borrowerClient;

    @Autowired
    public BorrowerService(IBorrowerClient borrowerClient) {
        this.borrowerClient = borrowerClient;
    }

    @Override
    public Borrower getBorrowerById(String accessToken, Integer borrowerId) {
        ResponseEntity<Borrower> borrowerResponseEntity = this.borrowerClient
                .getBorrower(accessToken, borrowerId);

        if (borrowerResponseEntity.getStatusCode().is2xxSuccessful()) {
            return borrowerResponseEntity.getBody();
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Borrower Id %d not found.", borrowerId));
    }
}

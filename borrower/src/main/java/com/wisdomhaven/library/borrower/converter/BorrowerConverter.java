package com.wisdomhaven.library.borrower.converter;

import com.wisdomhaven.library.borrower.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.borrower.model.Borrower;

public class BorrowerConverter {
    private BorrowerConverter() {}
    public static BorrowerResponseDTO toBorrowerResponseDTO(Borrower borrower) {
        return BorrowerResponseDTO
                .builder()
                .borrowerId(borrower.getBorrowerId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .build();
    }
}

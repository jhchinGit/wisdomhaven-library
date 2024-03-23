package com.wisdomhaven.library.converter;

import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.model.Borrower;

public class BorrowerConverter {
    private BorrowerConverter() {}
    public static BorrowerResponseDTO ToBorrowerResponseDTO(Borrower borrower) {
        return BorrowerResponseDTO
                .builder()
                .id(borrower.getId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .build();
    }
}

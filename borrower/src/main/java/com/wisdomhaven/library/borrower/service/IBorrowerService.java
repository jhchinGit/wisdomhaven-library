package com.wisdomhaven.library.borrower.service;

import com.wisdomhaven.library.borrower.dto.response.BorrowerResponseDTO;
import org.springframework.data.domain.Page;

public interface IBorrowerService {
    Page<BorrowerResponseDTO> getBorrowers(Integer borrowerId,
                                           String name,
                                           String email,
                                           Integer pageNumber,
                                           Integer pageSize,
                                           String orderBy);

    BorrowerResponseDTO getBorrower(Integer borrowerId);
    BorrowerResponseDTO createBorrower(String name, String email);
}

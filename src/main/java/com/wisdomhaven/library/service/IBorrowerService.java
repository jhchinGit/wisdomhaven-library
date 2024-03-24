package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import org.springframework.data.domain.Page;

public interface IBorrowerService {
    Page<BorrowerResponseDTO> getBorrowers(Integer borrowerId,
                                           String name,
                                           String email,
                                           Integer pageNumber,
                                           Integer pageSize,
                                           String orderBy);
    BorrowerResponseDTO createBorrower(String name, String email);
}

package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBorrowerService {
    Page<BorrowerResponseDTO> getBorrowers(Integer id,
                                           String name,
                                           String email,
                                           Integer pageNumber,
                                           Integer pageSize);
    BorrowerResponseDTO createBorrower(String name, String email);
}

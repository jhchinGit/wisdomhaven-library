package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;

import java.util.List;

public interface IBorrowerService {
    List<BorrowerResponseDTO> getBorrowers(Integer id, String name, String email);
    BorrowerResponseDTO createBorrower(String name, String email);
}

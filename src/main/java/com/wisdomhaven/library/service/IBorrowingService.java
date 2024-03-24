package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BorrowingResponseDTO;

import java.util.List;

public interface IBorrowingService {
    BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList);
    String returnAllBooks(Integer transactionId);
    String returnBookByBookId(Integer transactionId, Integer bookId);
}

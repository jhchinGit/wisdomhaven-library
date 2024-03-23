package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BorrowingResponseDTO;

import java.util.List;

public interface IBorrowingService {
    BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList);
    void returnAllBooks(Integer transactionId);
    void returnBookByBookId(Integer transactionId, Integer bookId);
}

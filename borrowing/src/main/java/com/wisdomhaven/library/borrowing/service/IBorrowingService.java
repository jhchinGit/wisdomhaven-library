package com.wisdomhaven.library.borrowing.service;

import com.wisdomhaven.library.borrowing.dto.response.BorrowingResponseDTO;

import java.util.List;

public interface IBorrowingService {
    BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList);
    String returnAllBooks(Integer borrowingId);
    String returnBookByBookId(Integer borrowingId, Integer bookId);
}

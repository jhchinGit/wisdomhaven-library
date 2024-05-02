package com.wisdomhaven.library.borrowing.service;

import com.wisdomhaven.library.borrowing.dto.response.BorrowingResponseDTO;

import java.util.List;

public interface IBorrowingService {
    BorrowingResponseDTO createBorrowing(String accessToken, Integer borrowerId, List<Integer> bookIdList);
    String returnAllBooks(String accessToken, Integer borrowingId);
    String returnBookByBookId(String accessToken, Integer borrowingId, Integer bookId);
}

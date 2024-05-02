package com.wisdomhaven.library.borrowing.service;

import com.wisdomhaven.library.borrowing.dto.apiResult.Borrower;

public interface IBorrowerService {
    Borrower getBorrowerById(String accessToken, Integer borrowerId);
}

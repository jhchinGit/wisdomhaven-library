package com.wisdomhaven.library.borrower.service;

public interface IAuthenticatorService {
    boolean verifyAccessToken(String accessToken);
}

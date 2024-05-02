package com.wisdomhaven.library.borrowing.service;

public interface IAuthenticatorService {
    boolean verifyAccessToken(String accessToken);
}

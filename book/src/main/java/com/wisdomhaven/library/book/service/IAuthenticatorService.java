package com.wisdomhaven.library.book.service;

public interface IAuthenticatorService {
    boolean verifyAccessToken(String accessToken);
}

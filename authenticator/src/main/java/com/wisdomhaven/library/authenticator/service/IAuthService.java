package com.wisdomhaven.library.authenticator.service;

import com.wisdomhaven.library.authenticator.dto.response.TokenResponseDTO;

public interface IAuthService {
    TokenResponseDTO authenticateUser(String username, String password);
    TokenResponseDTO refreshToken(String refreshToken);
}

package com.wisdomhaven.library.authenticator.service;

import com.wisdomhaven.library.authenticator.dto.response.TokenResponseDTO;
import com.wisdomhaven.library.authenticator.model.User;

public interface ITokenService {
    TokenResponseDTO generateToken(User user);
    TokenResponseDTO refreshToken(String refreshToken);
}

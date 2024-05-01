package com.wisdomhaven.library.authenticator.service.impl;

import com.wisdomhaven.library.authenticator.dto.response.AccessTokenVerificationResponseDTO;
import com.wisdomhaven.library.authenticator.dto.response.TokenResponseDTO;
import com.wisdomhaven.library.authenticator.model.User;
import com.wisdomhaven.library.authenticator.repository.IUserRepository;
import com.wisdomhaven.library.authenticator.service.IAuthService;
import com.wisdomhaven.library.authenticator.service.ITokenService;
import com.wisdomhaven.library.authenticator.util.ShaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements IAuthService {
    private static final String INVALID_LOGIN = "The provided username or password is not valid.";
    private final IUserRepository userRepository;
    private final ITokenService tokenService;

    @Autowired
    public AuthService(IUserRepository userRepository, ITokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public TokenResponseDTO authenticateUser(String username, String password) {
        return this.tokenService.generateToken(getUser(username, password));
    }

    @Override
    public TokenResponseDTO refreshToken(String refreshToken) {
        return this.tokenService.refreshToken(refreshToken);
    }

    @Override
    public AccessTokenVerificationResponseDTO verifyAccessToken(String accessToken) {
        boolean isValid = this.tokenService.verifyAccessToken(accessToken);

        return AccessTokenVerificationResponseDTO
                .builder()
                .isValid(isValid)
                .build();
    }

    private User getUser(String username, String password) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_LOGIN));

        ShaUtils shaUtils = new ShaUtils();
        String salt = user.getSalt();
        String saltPassword = password + salt;
        String hashPassword = shaUtils.digestAsHex(saltPassword);

        if (!hashPassword.equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_LOGIN);
        }

        return user;
    }
}

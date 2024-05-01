package com.wisdomhaven.library.authenticator.controller;

import com.wisdomhaven.library.authenticator.dto.request.AccessTokenVerificationRequest;
import com.wisdomhaven.library.authenticator.dto.request.RefreshTokenRequest;
import com.wisdomhaven.library.authenticator.dto.request.TokenRequest;
import com.wisdomhaven.library.authenticator.dto.response.TokenResponseDTO;
import com.wisdomhaven.library.authenticator.service.IAuthService;
import com.wisdomhaven.library.authenticator.util.RequestUtil;
import com.wisdomhaven.library.authenticator.util.ResponseUtil;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "auth")
public class AuthController {
    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/token",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public ResponseEntity authenticateUser(TokenRequest tokenRequest) {
        RequestUtil.validate(tokenRequest);

        TokenResponseDTO tokenResponseDTO = this.authService.authenticateUser(
                tokenRequest.username(),
                tokenRequest.password());

        return ResponseUtil.buildResponseEntity(tokenResponseDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/token/refresh",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RequestUtil.validate(refreshTokenRequest);

        TokenResponseDTO tokenResponseDTO = this.authService.refreshToken(refreshTokenRequest.refreshToken());

        return ResponseUtil.buildResponseEntity(tokenResponseDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/token/verify",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public ResponseEntity verifyAccessToken(@RequestBody AccessTokenVerificationRequest accessTokenVerificationRequest) {
        RequestUtil.validate(accessTokenVerificationRequest);

        return ResponseUtil.buildResponseEntity(this.authService.verifyAccessToken(accessTokenVerificationRequest.accessToken()), HttpStatus.OK);
    }
}

package com.wisdomhaven.library.authenticator.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.wisdomhaven.library.authenticator.config.SecretConfiguration;
import com.wisdomhaven.library.authenticator.dto.response.TokenResponseDTO;
import com.wisdomhaven.library.authenticator.model.User;
import com.wisdomhaven.library.authenticator.service.ITokenService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {
    private static final String ISSUER = "WisdomHaven";
    private static final String JWT_SUBJECT = "General Usage";
    private final SecretConfiguration secretConfiguration;

    @Autowired
    public TokenService(SecretConfiguration secretConfiguration) {
        this.secretConfiguration = secretConfiguration;
    }

    @Override
    public TokenResponseDTO generateToken(User user) {
        TokenAlgorithm algorithm = getAlgorithm();
        String uuid = UUID.randomUUID().toString();
        String authToken = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(JWT_SUBJECT)
                .withClaim("username", user.getUsername())
                .withClaim("userId", user.getUserId())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                .withJWTId(uuid)
                .sign(algorithm.accessTokenAlgorithm());
        String refreshToken = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(JWT_SUBJECT)
                .withClaim("username", user.getUsername())
                .withClaim("userId", user.getUserId())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(6).toInstant())
                .withJWTId(uuid)
                .sign(algorithm.refreshTokenAlgorithm());

        return TokenResponseDTO
                .builder()
                .accessToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponseDTO refreshToken(String refreshToken) {
        TokenAlgorithm algorithm = getAlgorithm();
        String uuid = UUID.randomUUID().toString();
        JWTVerifier verifier = JWT.require(algorithm.refreshTokenAlgorithm())
                .withIssuer(ISSUER)
                .build();

        try {
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String newAuthToken = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(JWT_SUBJECT)
                    .withClaim("username", decodedJWT.getClaim("username").asString())
                    .withClaim("userId", decodedJWT.getClaim("userId").asInt())
                    .withIssuedAt(ZonedDateTime.now().toInstant())
                    .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                    .withJWTId(uuid)
                    .sign(algorithm.accessTokenAlgorithm());
            String newRefreshToken = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(JWT_SUBJECT)
                    .withClaim("username", decodedJWT.getClaim("username").asString())
                    .withClaim("userId", decodedJWT.getClaim("userId").asInt())
                    .withIssuedAt(ZonedDateTime.now().toInstant())
                    .withExpiresAt(ZonedDateTime.now().plusHours(6).toInstant())
                    .withJWTId(uuid)
                    .sign(algorithm.refreshTokenAlgorithm());

            return TokenResponseDTO
                    .builder()
                    .accessToken(newAuthToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.");
        }
    }

    @Override
    public boolean verifyAccessToken(String accessToken) {
        if (accessToken == null) {
            return false;
        }

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.secretConfiguration.getJwtAccessTokenKey()))
                .withIssuer(ISSUER)
                .build();

        try {
            verifier.verify(accessToken);
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }

    private TokenAlgorithm getAlgorithm() {
        return TokenAlgorithm
                .builder()
                .accessTokenAlgorithm(Algorithm.HMAC256(this.secretConfiguration.getJwtAccessTokenKey()))
                .refreshTokenAlgorithm(Algorithm.HMAC256(this.secretConfiguration.getJwtRefreshTokenKey()))
                .build();
    }

    @Builder
    private record TokenAlgorithm(Algorithm accessTokenAlgorithm, Algorithm refreshTokenAlgorithm) {

    }
}

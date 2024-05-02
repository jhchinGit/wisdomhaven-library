package com.wisdomhaven.library.book.service.impl;

import com.wisdomhaven.library.book.client.IAuthenticatorClient;
import com.wisdomhaven.library.book.dto.request.AccessTokenVerificationRequest;
import com.wisdomhaven.library.book.service.IAuthenticatorService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService implements IAuthenticatorService {
    private final ObjectProvider<IAuthenticatorClient> authenticatorClientObjectProvider;

    @Autowired
    public AuthenticatorService(ObjectProvider<IAuthenticatorClient> authenticatorClientObjectProvider) {
        this.authenticatorClientObjectProvider = authenticatorClientObjectProvider;
    }

    @Override
    public boolean verifyAccessToken(String accessToken) {
        if (accessToken == null) {
            return false;
        }

        String bearerAccessToken = String.format("Bearer %s", accessToken);

        AccessTokenVerificationRequest accessTokenVerificationRequest = AccessTokenVerificationRequest
                .builder()
                .accessToken(accessToken)
                .build();

        ResponseEntity verificationResponseDTOResponseEntity;

        try {
            verificationResponseDTOResponseEntity = getAuthenticatorClient()
                    .verifyAccessToken(bearerAccessToken, accessTokenVerificationRequest);
        } catch (Exception e) {
            return false;
        }

        return verificationResponseDTOResponseEntity.getStatusCode().is2xxSuccessful();
    }

    private IAuthenticatorClient getAuthenticatorClient() {
        return this.authenticatorClientObjectProvider.getIfAvailable();
    }
}

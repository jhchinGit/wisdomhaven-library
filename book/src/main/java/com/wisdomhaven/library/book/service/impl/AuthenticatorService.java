package com.wisdomhaven.library.book.service.impl;

import com.wisdomhaven.library.book.client.IAuthenticatorClient;
import com.wisdomhaven.library.book.dto.apiResult.AccessTokenVerificationResponseDTO;
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

        AccessTokenVerificationRequest accessTokenVerificationRequest = AccessTokenVerificationRequest
                .builder()
                .accessToken(accessToken)
                .build();

        ResponseEntity<AccessTokenVerificationResponseDTO> verificationResponseDTOResponseEntity =
                getAuthenticatorClient().verifyAccessToken(accessTokenVerificationRequest);

        if (verificationResponseDTOResponseEntity.getStatusCode().is2xxSuccessful()) {
            return verificationResponseDTOResponseEntity.getBody().isValid();
        }

        return false;
    }

    private IAuthenticatorClient getAuthenticatorClient() {
        return this.authenticatorClientObjectProvider.getIfAvailable();
    }
}

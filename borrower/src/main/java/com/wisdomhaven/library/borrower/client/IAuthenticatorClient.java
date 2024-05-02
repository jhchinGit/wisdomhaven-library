package com.wisdomhaven.library.borrower.client;

import com.wisdomhaven.library.borrower.dto.request.AccessTokenVerificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="authenticatorClient", url = "localhost:8093/auth/token")
public interface IAuthenticatorClient {

    @PostMapping(value = "/verify",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity verifyAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody AccessTokenVerificationRequest request);
}

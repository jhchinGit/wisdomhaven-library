package com.wisdomhaven.library.book.client;

import com.wisdomhaven.library.book.dto.apiResult.AccessTokenVerificationResponseDTO;
import com.wisdomhaven.library.book.dto.request.AccessTokenVerificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="authenticatorClient", url = "localhost:8093/auth/token")
public interface IAuthenticatorClient {

    @PostMapping(value = "/verify",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<AccessTokenVerificationResponseDTO> verifyAccessToken(@RequestBody AccessTokenVerificationRequest request);
}

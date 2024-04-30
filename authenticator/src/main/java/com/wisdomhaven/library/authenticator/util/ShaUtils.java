package com.wisdomhaven.library.authenticator.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Base64;

public class ShaUtils {
    private static final int NONCE_BYTES = 32;

    public String digestAsHex(String originalString) {
        return new DigestUtils("SHA3-256").digestAsHex(originalString);
    }

    public String getRandomNonceString() {
        return Base64.getEncoder().encodeToString(getRandomByteArrayNonce());
    }

    private byte[] getRandomByteArrayNonce() {
        byte[] nonce = new byte[NONCE_BYTES];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}

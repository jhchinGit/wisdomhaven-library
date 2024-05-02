package com.wisdomhaven.library.borrower.util;

public final class TokenUtil {
    private TokenUtil() {}

    public static String convertToBearerToken(String accessToken) {
        return String.format("Bearer %s", accessToken);
    }
}

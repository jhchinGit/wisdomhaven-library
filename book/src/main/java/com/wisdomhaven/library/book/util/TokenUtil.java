package com.wisdomhaven.library.book.util;

public final class TokenUtil {
    private TokenUtil() {}

    public static String convertToBearerToken(String accessToken) {
        return String.format("Bearer %s", accessToken);
    }
}

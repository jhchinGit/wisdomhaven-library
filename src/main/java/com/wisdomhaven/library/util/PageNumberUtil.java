package com.wisdomhaven.library.util;

public final class PageNumberUtil {
    private PageNumberUtil() {}

    public static int getPageNumber(Integer pageNumber) {
        return pageNumber == null ? 0 : pageNumber - 1;
    }
}

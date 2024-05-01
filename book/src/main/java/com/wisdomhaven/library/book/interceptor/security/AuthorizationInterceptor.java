package com.wisdomhaven.library.book.interceptor.security;

import com.wisdomhaven.library.book.service.IAuthenticatorService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final IAuthenticatorService authenticatorService;

    @Autowired
    public AuthorizationInterceptor(IAuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (handler instanceof HandlerMethod handlerMethod &&
                handlerMethod.getMethodAnnotation(PermitAll.class) != null) {
            return true;
        }

        String accessToken = extractBearerToken(request);

        if (!this.authenticatorService.verifyAccessToken(accessToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Access to the requested resource is unauthorized. Please authenticate to access this resource.");
        }

        return true;
    }

    private static String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}

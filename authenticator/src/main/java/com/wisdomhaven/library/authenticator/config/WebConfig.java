package com.wisdomhaven.library.authenticator.config;

import com.wisdomhaven.library.authenticator.interceptor.security.AuthorizationInterceptor;
import com.wisdomhaven.library.authenticator.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ITokenService tokenService;

    @Autowired
    public WebConfig(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(this.tokenService));
    }
}
package com.wisdomhaven.library.borrower.config;

import com.wisdomhaven.library.borrower.interceptor.security.AuthorizationInterceptor;
import com.wisdomhaven.library.borrower.service.IAuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IAuthenticatorService authenticatorService;

    @Autowired
    public WebConfig(IAuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(this.authenticatorService));
    }
}
package com.wisdomhaven.library.authenticator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secret")
@Data
public class SecretConfiguration {
    private String jwtAccessTokenKey;
    private String jwtRefreshTokenKey;
}

package com.wisdomhaven.library.borrowing.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDatabase {
    @Bean
    CommandLineRunner setupMockData() {
        return args -> {
            // do nothing
        };
    }
}

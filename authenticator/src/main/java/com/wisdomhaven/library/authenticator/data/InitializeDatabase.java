package com.wisdomhaven.library.authenticator.data;

import com.wisdomhaven.library.authenticator.model.User;
import com.wisdomhaven.library.authenticator.repository.IUserRepository;
import com.wisdomhaven.library.authenticator.util.ShaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDatabase {
    private final IUserRepository userRepository;

    @Autowired
    public InitializeDatabase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner setupMockData() {
        return args -> {
            setupUserMockDate();
        };
    }

    private void setupUserMockDate() {
        saveUser("user1", "user1@gmail.com");
        saveUser("user2", "user2@gmail.com");
        saveUser("user3", "user3@gmail.com");
        saveUser("user4", "user4@gmail.com");
        saveUser("user5", "user5@gmail.com");
    }

    private void saveUser(String username, String email) {
        ShaUtils shaUtils = new ShaUtils();
        String salt = shaUtils.getRandomNonceString();
        String saltPassword = "abcd1234" + salt;
        String hashPassword = shaUtils.digestAsHex(saltPassword);

        this.userRepository.save(
                User.builder()
                        .username(username.toLowerCase())
                        .password(hashPassword)
                        .email(email.toLowerCase())
                        .isActive(true)
                        .salt(salt)
                        .build());
    }
}

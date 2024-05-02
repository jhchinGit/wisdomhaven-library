package com.wisdomhaven.library.authenticator.service.impl;

import com.wisdomhaven.library.authenticator.model.User;
import com.wisdomhaven.library.authenticator.repository.IUserRepository;
import com.wisdomhaven.library.authenticator.service.IUserService;
import com.wisdomhaven.library.authenticator.util.ShaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements IUserService {
    private static final String INVALID_LOGIN = "The provided username or password is not valid.";
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String username, String password, String email) {
        if (this.userRepository.existsByUsername(username.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Username %s is used.", username));
        }

        if (this.userRepository.existsByEmail(email.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Email %s is used.", email));
        }

        ShaUtils shaUtils = new ShaUtils();
        String salt = shaUtils.getRandomNonceString();
        String saltPassword = password + salt;
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

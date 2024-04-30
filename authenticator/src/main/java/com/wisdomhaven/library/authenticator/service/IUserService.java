package com.wisdomhaven.library.authenticator.service;

public interface IUserService {
    void createUser(String username, String password, String email);
    String loginUser(String username, String password);
}

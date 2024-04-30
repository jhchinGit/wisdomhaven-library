package com.wisdomhaven.library.authenticator.repository;

import com.wisdomhaven.library.authenticator.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface IUserRepository extends ListCrudRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}

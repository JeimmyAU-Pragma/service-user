package com.pragma.user.domain.spi;

import com.pragma.user.domain.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {
    UserModel saveUser(UserModel user);

    List<UserModel> getAllUsers();

    UserModel getUserById(Long id);

   Optional<UserModel> findByEmail(String email);
}
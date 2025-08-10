package com.pragma.user.domain.api;
//CRUD

import com.pragma.user.domain.model.UserModel;

import java.util.List;

public interface IUserServicePort {

    void saveEmployee(UserModel user);

    void saveUser(UserModel user);

    List<UserModel> getAllUsers();

    UserModel getUserById(Long userId);

}
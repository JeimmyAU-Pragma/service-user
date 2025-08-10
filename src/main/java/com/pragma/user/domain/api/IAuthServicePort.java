package com.pragma.user.domain.api;


import com.pragma.user.domain.model.UserModel;

public interface IAuthServicePort {

    String login(String email, String password);
    UserModel getUserByEmail(String email);
}

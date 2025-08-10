package com.pragma.user.application.handler;

import com.pragma.user.application.dto.response.UserResponseDto;

public interface IAuthHandler {
    String login(String email, String password);
    UserResponseDto getUserByEmail(String email);
}

package com.pragma.user.application.handler.impl;

import com.pragma.user.application.dto.response.UserResponseDto;
import com.pragma.user.application.handler.IAuthHandler;
import com.pragma.user.application.mapper.IUserResponseMapper;
import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IUserServicePort userServicePort;
    private final IUserResponseMapper userResponseMapper;
    private final IAuthServicePort authServicePort;

    @Override
    public String login(String email, String password) {
        return authServicePort.login(email, password);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserModel user = authServicePort.getUserByEmail(email);
        return userResponseMapper.toResponse(user);
    }
}

package com.pragma.user.application.handler.impl;

import com.pragma.user.application.dto.request.EmployeeRequestDto;
import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.application.dto.response.UserResponseDto;
import com.pragma.user.application.handler.IUserHandler;
import com.pragma.user.application.mapper.IUserRequestMapper;
import com.pragma.user.application.mapper.IUserResponseMapper;
import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

//    @Override
//    public void saveUser(UserRequestDto userRequestDto) {
//        UserModel user = userRequestMapper.toUser(userRequestDto);
//        userServicePort.saveOwner(user);
//    }
    @Override
    public void saveOwner(UserRequestDto userRequestDto) {
        UserModel user = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveOwner(user);
    }
    @Override
    public void saveEmployee(EmployeeRequestDto employeeRequestDto) {
        UserModel user = userRequestMapper.toEmployee(employeeRequestDto);
        userServicePort.saveEmployee(user);
    }
    @Override
    public void saveClient(EmployeeRequestDto employeeRequestDto) {
        UserModel client = userRequestMapper.toEmployee(employeeRequestDto);
        userServicePort.saveEmployee(client);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userResponseMapper.toResponseList(userServicePort.getAllUsers());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        return userResponseMapper.toResponse(userServicePort.getUserById(userId));
    }



}

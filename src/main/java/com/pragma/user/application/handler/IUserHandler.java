package com.pragma.user.application.handler;

import com.pragma.user.application.dto.request.EmployeeRequestDto;
import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);

    void createOwner(UserRequestDto dto, String callerRole);

    void createEmployee(EmployeeRequestDto dto, String callerRole);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

}
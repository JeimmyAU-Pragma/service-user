package com.pragma.user.application.dto.response;

import com.pragma.user.application.dto.request.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private RoleDto role;
}

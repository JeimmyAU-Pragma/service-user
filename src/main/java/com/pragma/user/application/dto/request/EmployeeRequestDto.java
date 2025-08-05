package com.pragma.user.application.dto.request;

import com.pragma.user.domain.model.Rol;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequestDto {
    private String firstName;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private String email;
    private String password;
    private Rol rol;

}
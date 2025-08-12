package com.pragma.user.application.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeRequestDto {
    private String firstName;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private String email;
    private String password;
}
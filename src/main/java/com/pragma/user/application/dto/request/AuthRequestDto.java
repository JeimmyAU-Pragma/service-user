package com.pragma.user.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    private String email;
    private String password;

}

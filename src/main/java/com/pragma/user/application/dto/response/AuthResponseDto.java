package com.pragma.user.application.dto.response;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private final String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}

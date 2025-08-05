package com.pragma.user.infrastructure.input.rest;

import com.pragma.user.application.dto.request.AuthRequestDto;
import com.pragma.user.application.dto.response.AuthResponseDto;
import com.pragma.user.domain.api.IAuthServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthServicePort authServicePort;

    public AuthController(IAuthServicePort authServicePort) {
        this.authServicePort = authServicePort;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        String token = authServicePort.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }


}

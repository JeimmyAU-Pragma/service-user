package com.pragma.user.infrastructure.input.rest;

import com.pragma.user.application.dto.request.AuthRequestDto;
import com.pragma.user.application.dto.response.AuthResponseDto;
import com.pragma.user.application.dto.response.UserResponseDto;
import com.pragma.user.application.handler.IAuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        String token = authHandler.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        return ResponseEntity.ok(authHandler.getUserByEmail(email));
    }

}

package com.pragma.user.domain.usecase;

import com.pragma.user.domain.model.Rol;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthUseCase authUseCase;

    private final String email = "test@email.com";
    private final String rawPassword = "123456";
    private final String encodedPassword = "$2a$10$encoded";
    private final String expectedToken = "jwt.token.here";

    @Test
    void loginShouldReturnTokenWhenCredentialsAreValid() {
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(Rol.ADMINISTRADOR);

        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn(expectedToken);

        String token = authUseCase.login(email, rawPassword);

        assertEquals(expectedToken, token);
    }

    @Test
    void loginShouldThrowExceptionWhenUserNotFound() {
        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authUseCase.login(email, rawPassword);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void loginShouldThrowExceptionWhenPasswordInvalid() {
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(Rol.ADMINISTRADOR);

        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authUseCase.login(email, rawPassword);
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}

package com.pragma.user.domain.usecase;

import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.pragma.user.domain.util.DomainConstants.INVALID_CREDENTIALS;
import static com.pragma.user.domain.util.DomainConstants.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoder;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private AuthUseCase authUseCase;

    private UserModel validUser;

    @BeforeEach
    void setUp() {
        RoleModel role = new RoleModel(1L, "ADMINISTRADOR", "Admin role");
        validUser = new UserModel(
                1L,
                "Jeimmy",
                "Aguilar",
                "123456789",
                "+573001112233",
                LocalDate.of(1995, 8, 5),
                "admin@admin.com",
                "rawPass123",
                role
        );
    }

    @Test
    void login_ok() {
        when(userPersistencePort.findByEmail("admin@admin.com")).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches("rawPass123", "rawPass123")).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn("jwt123");

        String token = authUseCase.login("admin@admin.com", "rawPass123");

        assertEquals("jwt123", token);
    }

    @Test
    void login_badPassword() {
        when(userPersistencePort.findByEmail("admin@admin.com")).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        DomainException ex = assertThrows(DomainException.class,
                () -> authUseCase.login("admin@admin.com", "wrong"));

        assertEquals(INVALID_CREDENTIALS, ex.getMessage());
    }

    @Test
    void login_noUser() {
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class,
                () -> authUseCase.login("no@exists.com", "123"));

        assertEquals(USER_NOT_FOUND + "no@exists.com", ex.getMessage());
    }

    @Test
    void getUser_ok() {
        when(userPersistencePort.findByEmail("admin@admin.com")).thenReturn(Optional.of(validUser));

        assertEquals(validUser, authUseCase.getUserByEmail("admin@admin.com"));
    }

    @Test
    void getUser_noUser() {
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> authUseCase.getUserByEmail("no@exists.com"));
    }
}

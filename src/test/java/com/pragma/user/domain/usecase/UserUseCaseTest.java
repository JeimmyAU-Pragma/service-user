package com.pragma.user.domain.usecase;

import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static com.pragma.user.domain.util.DomainConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pragma.user.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IPasswordEncoderPort passwordEncoderPort;
    @Mock private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel validUser;

    @BeforeEach
    void setUp() {
        RoleModel role = new RoleModel(1L, "ADMINISTRADOR", "Rol administrador");
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
    void shouldsaveOwnerSuccessfully() {
        RoleModel admin = new RoleModel(99L, "PROPIETARIO", "Propietario del sistema");
        when(rolePersistencePort.findByName("PROPIETARIO")).thenReturn(Optional.of(admin));
        when(passwordEncoderPort.encode("rawPass123")).thenReturn("encryptedPass");

        userUseCase.saveOwner(validUser);

        verify(rolePersistencePort).findByName("PROPIETARIO");
        verify(passwordEncoderPort).encode("rawPass123");
        verify(userPersistencePort).saveUser(validUser);

        assertEquals(admin, validUser.getRole());
        assertEquals("encryptedPass", validUser.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsInvalid() {
        validUser.setIdentityDocument("12A456");

        DomainException ex = assertThrows(DomainException.class,
                () -> userUseCase.saveOwner(validUser));

        assertEquals(INVALID_DOCUMENT, ex.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        validUser.setEmail("invalid-email");
        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwner(validUser));
        assertEquals(INVALID_EMAIL, ex.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void shouldThrowExceptionWhenUserIsMinor() {
        validUser.setBirthDate(LocalDate.now().minusYears(15));
        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwner(validUser));
        assertEquals(MESSAGE_ADULT, ex.getMessage());
        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUser(any());
    }



    @Test
    void shouldGetAllUsers() {
        when(userPersistencePort.getAllUsers()).thenReturn(List.of(validUser));
        List<UserModel> result = userUseCase.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("Jeimmy", result.get(0).getFirstName());
        verify(userPersistencePort).getAllUsers();
    }

    @Test
    void shouldGetUserById() {
        when(userPersistencePort.getUserById(1L)).thenReturn(validUser);
        UserModel result = userUseCase.getUserById(1L);
        assertEquals("Jeimmy", result.getFirstName());
        verify(userPersistencePort).getUserById(1L);
    }



}

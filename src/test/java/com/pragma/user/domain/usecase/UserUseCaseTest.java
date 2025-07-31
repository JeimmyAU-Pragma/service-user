package com.pragma.user.domain.usecase;

import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.Rol;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {


        @Mock
        private IUserPersistencePort userPersistencePort;

        @Mock
        private IPasswordEncoderPort passwordEncoderPort;

        @InjectMocks
        private UserUseCase userUseCase;

        private UserModel validUser;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            validUser = new UserModel();
            validUser.setFirstName("Ana");
            validUser.setLastName("Lopez");
            validUser.setEmail("ana@example.com");
            validUser.setPassword("rawPass123");
            validUser.setBirthDate(LocalDate.now().minusYears(25));
            validUser.setPhoneNumber("+573005678123");
            validUser.setIdentityDocument("123456789");
        }

        @Test
        void shouldSaveOwnerSuccessfully() {
            when(passwordEncoderPort.encode("rawPass123")).thenReturn("encryptedPass");

            userUseCase.saveOwner(validUser, "rawPass123");

            verify(passwordEncoderPort).encode("rawPass123");
            verify(userPersistencePort).saveUser(validUser);
            assertEquals(Rol.PROPIETARIO, validUser.getRol());
            assertEquals("encryptedPass", validUser.getPassword());
        }

        @Test
        void shouldThrowExceptionWhenEmailIsInvalid() {
            validUser.setEmail("invalid-email");

            DomainException exception = assertThrows(DomainException.class, () ->
                    userUseCase.saveOwner(validUser, "rawPass123"));

            assertEquals("Correo inválido", exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        void shouldThrowExceptionWhenUserIsMinor() {
            validUser.setBirthDate(LocalDate.now().minusYears(15));

            DomainException exception = assertThrows(DomainException.class, () ->
                    userUseCase.saveOwner(validUser, "rawPass123"));

            assertEquals("Debe ser mayor de edad", exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenPasswordIsEmpty() {
            assertThrows(IllegalArgumentException.class, () ->
                    userUseCase.saveOwner(validUser, ""));
        }

        @Test
        void shouldGetAllUsers() {
            List<UserModel> expectedList = List.of(validUser);
            when(userPersistencePort.getAllUsers()).thenReturn(expectedList);

            List<UserModel> result = userUseCase.getAllUsers();

            assertEquals(1, result.size());
            assertEquals("Ana", result.get(0).getFirstName());
        }

        @Test
        void shouldGetUserById() {
            when(userPersistencePort.getUserById(1L)).thenReturn(validUser);

            UserModel result = userUseCase.getUserById(1L);

            assertEquals("Ana", result.getFirstName());
            verify(userPersistencePort).getUserById(1L);
        }
    }


package com.pragma.user.domain.usecase;
// se hacen las validaciones en el caso de uso

import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.Rol;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private IPasswordEncoderPort passwordEncoder;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserModel user, Rol rol) {
        validate(user);
        user.setRol(rol);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    /*
        @Override
        public void saveOwner(UserModel user, Rol rol) {
            validate(user);

            user.setRol(rol);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userPersistencePort.saveUser(user);
        }*/
    @Override
    public List<UserModel> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }


    @Override
    public UserModel getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    private void validate(UserModel user) {
        if (!user.getEmail().matches("^\\S+@\\S+\\.\\S+$")) {
            throw new DomainException("Correo inválido");
        }
        if (!user.getIdentityDocument().matches("\\d+")) {
            throw new DomainException("Documento debe ser numérico");
        }
        if (!user.getPhoneNumber().matches("^\\+?\\d{1,13}$")) {
            throw new DomainException("Celular inválido");
        }
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new DomainException("Debe ser mayor de edad");
        }

    }
}

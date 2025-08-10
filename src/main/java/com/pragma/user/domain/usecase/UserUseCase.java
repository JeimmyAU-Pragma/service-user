package com.pragma.user.domain.usecase;
// se hacen las validaciones en el caso de uso

import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IRolePersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.pragma.user.domain.util.DomainConstants.*;


public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoder;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoder, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
        this.rolePersistencePort = rolePersistencePort;
    }

//    @Override
//    public void saveUser(UserModel user, RoleModel role) {
//        validate(user);
//        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
//            throw new DomainException("Debe ser mayor de edad");
//        }
//        RoleModel roleActual = rolePersistencePort.getRoleById(role.getId());
//
//        user.setRol(roleActual);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userPersistencePort.saveUser(user);
//    }
//
//    @Override
//    public void saveEmployee(UserModel user, RoleModel role) {
//        validate(user);
//        user.setRol(rol);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userPersistencePort.saveUser(user);
//    }

    @Override
    public void saveEmployee(UserModel user) {
        validate(user);
        RoleModel role = rolePersistencePort.findByName("PROPIETARIO")
                .orElseThrow(() -> new IllegalArgumentException("Rol PROPIETARIO no existe"));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveUser(UserModel user) {
        validate(user);
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new DomainException(MESSAGE_ADULT);
        }
        RoleModel ownerRole = rolePersistencePort.findByName("ADMINISTRADOR")
                .orElseThrow(() -> new IllegalArgumentException("Rol  no existe"));

        user.setRole(ownerRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

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
            throw new DomainException(INVALID_EMAIL);
        }
        if (!user.getIdentityDocument().matches("\\d+")) {
            throw new DomainException(INVALID_DOCUMENT);
        }
        if (!user.getPhoneNumber().matches("^\\+?\\d{1,13}$")) {
            throw new DomainException(INVALID_PHONE_NUMBER);
        }


    }
}

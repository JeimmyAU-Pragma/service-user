package com.pragma.user.domain.usecase;

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

    @Override
    public void saveEmployee(UserModel user) {
        validate(user);
        RoleModel employee = rolePersistencePort.findByName(ROLE_EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException(ROLE_NOT_FOUND));
        user.setRole(employee);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveOwner(UserModel user) {
        validate(user);
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new DomainException(MESSAGE_ADULT);
        }
        RoleModel ownerRole = rolePersistencePort.findByName(ROLE_OWNER)
                .orElseThrow(() -> new IllegalArgumentException(ROLE_NOT_FOUND));

        user.setRole(ownerRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveClient(UserModel user) {
        validate(user);

        RoleModel ownerRole = rolePersistencePort.findByName(ROLE_CLIENT)
                .orElseThrow(() -> new IllegalArgumentException(ROLE_NOT_FOUND));

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
        if (userPersistencePort.findByEmail(user.getEmail()).isPresent()) {
            throw new DomainException(EMAIL_ALREADY_EXISTS);
        }

    }
}

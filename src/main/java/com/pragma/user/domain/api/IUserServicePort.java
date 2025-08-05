package com.pragma.user.domain.api;
//CRUD
import com.pragma.user.domain.model.Rol;
import com.pragma.user.domain.model.UserModel;

import java.util.List;

public interface IUserServicePort {

    //void saveOwner(UserModel user, String rawPassword);
    void saveUser(UserModel user, Rol rol);

    List<UserModel> getAllUsers();

    UserModel getUserById(Long userId);

}
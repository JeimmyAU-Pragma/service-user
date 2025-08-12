package com.pragma.user.domain.spi;


import com.pragma.user.domain.model.RoleModel;

import java.util.Optional;

public interface IRolePersistencePort {

    Optional<RoleModel> findById(Long id);
    Optional<RoleModel> findByName(String name);


}

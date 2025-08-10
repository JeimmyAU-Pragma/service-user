package com.pragma.user.infrastructure.out.jpa.adapter;

import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.spi.IRolePersistencePort;
import com.pragma.user.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.user.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;


    public Optional<RoleModel> findById(Long id) {
        return roleRepository.findById(id).map(roleEntityMapper::toRol);
    }

    public Optional<RoleModel> findByName(String name) {
        return roleRepository.findByName(name).map(roleEntityMapper::toRol);
    }
}
package com.pragma.user.infrastructure.out.jpa.adapter;

import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.infrastructure.exception.NoUserFoundException;
import com.pragma.user.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    public UserJpaAdapter(IUserEntityMapper userEntityMapper, IUserRepository userRepository) {
        this.userEntityMapper = userEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserModel saveUser(UserModel user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toUser(userEntity);
    }



    @Override
    public List<UserModel> getAllUsers() {
        List<UserEntity> entityList = userRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoUserFoundException();
        }
        return userEntityMapper.toUserList(entityList);
    }

    @Override
    public UserModel getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntityMapper.toUser(userEntity.orElse(null));
    }

}
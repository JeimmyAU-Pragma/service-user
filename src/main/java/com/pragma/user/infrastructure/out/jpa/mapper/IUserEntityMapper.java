package com.pragma.user.infrastructure.out.jpa.mapper;

import com.pragma.user.domain.model.UserModel;
import com.pragma.user.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel user);
    UserModel toUser(UserEntity userEntity);
    List<UserModel> toUserList(List<UserEntity> userEntityList);
}
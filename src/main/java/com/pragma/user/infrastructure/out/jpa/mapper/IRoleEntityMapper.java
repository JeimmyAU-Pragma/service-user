package com.pragma.user.infrastructure.out.jpa.mapper;

import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {
    RoleEntity toRolEntity(RoleModel roleModel);
    RoleModel toRol(RoleEntity roleEntity);
}

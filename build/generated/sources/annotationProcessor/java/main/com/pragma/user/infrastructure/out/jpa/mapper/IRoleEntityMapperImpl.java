package com.pragma.user.infrastructure.out.jpa.mapper;

import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.infrastructure.out.jpa.entity.RoleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-12T06:35:00-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class IRoleEntityMapperImpl implements IRoleEntityMapper {

    @Override
    public RoleEntity toRolEntity(RoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( roleModel.getId() );
        roleEntity.setName( roleModel.getName() );
        roleEntity.setDescription( roleModel.getDescription() );

        return roleEntity;
    }

    @Override
    public RoleModel toRol(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = roleEntity.getId();
        name = roleEntity.getName();
        description = roleEntity.getDescription();

        RoleModel roleModel = new RoleModel( id, name, description );

        return roleModel;
    }
}

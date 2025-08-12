package com.pragma.user.infrastructure.out.jpa.mapper;

import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.user.infrastructure.out.jpa.entity.UserEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-12T06:35:00-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class IUserEntityMapperImpl implements IUserEntityMapper {

    @Override
    public UserEntity toEntity(UserModel user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setIdentityDocument( user.getIdentityDocument() );
        userEntity.setPhoneNumber( user.getPhoneNumber() );
        userEntity.setBirthDate( user.getBirthDate() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setRole( roleModelToRoleEntity( user.getRole() ) );

        return userEntity;
    }

    @Override
    public UserModel toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String identityDocument = null;
        String phoneNumber = null;
        LocalDate birthDate = null;
        String email = null;
        String password = null;
        RoleModel role = null;

        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        identityDocument = userEntity.getIdentityDocument();
        phoneNumber = userEntity.getPhoneNumber();
        birthDate = userEntity.getBirthDate();
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        role = roleEntityToRoleModel( userEntity.getRole() );

        UserModel userModel = new UserModel( id, firstName, lastName, identityDocument, phoneNumber, birthDate, email, password, role );

        return userModel;
    }

    @Override
    public List<UserModel> toUserList(List<UserEntity> userEntityList) {
        if ( userEntityList == null ) {
            return null;
        }

        List<UserModel> list = new ArrayList<UserModel>( userEntityList.size() );
        for ( UserEntity userEntity : userEntityList ) {
            list.add( toUser( userEntity ) );
        }

        return list;
    }

    protected RoleEntity roleModelToRoleEntity(RoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( roleModel.getId() );
        roleEntity.setName( roleModel.getName() );
        roleEntity.setDescription( roleModel.getDescription() );

        return roleEntity;
    }

    protected RoleModel roleEntityToRoleModel(RoleEntity roleEntity) {
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

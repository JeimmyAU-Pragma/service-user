package com.pragma.user.infrastructure.out.jpa.mapper;

import com.pragma.user.domain.model.UserModel;
import com.pragma.user.infrastructure.out.jpa.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T14:25:26-0500",
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

        userEntity.setUserId( user.getUserId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setIdentityDocument( user.getIdentityDocument() );
        userEntity.setPhoneNumber( user.getPhoneNumber() );
        userEntity.setBirthDate( user.getBirthDate() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setRol( user.getRol() );

        return userEntity;
    }

    @Override
    public UserModel toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setUserId( userEntity.getUserId() );
        userModel.setFirstName( userEntity.getFirstName() );
        userModel.setLastName( userEntity.getLastName() );
        userModel.setIdentityDocument( userEntity.getIdentityDocument() );
        userModel.setPhoneNumber( userEntity.getPhoneNumber() );
        userModel.setBirthDate( userEntity.getBirthDate() );
        userModel.setEmail( userEntity.getEmail() );
        userModel.setPassword( userEntity.getPassword() );
        userModel.setRol( userEntity.getRol() );

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
}

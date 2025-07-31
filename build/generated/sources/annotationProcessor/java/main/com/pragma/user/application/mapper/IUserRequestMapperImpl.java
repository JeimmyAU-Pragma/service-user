package com.pragma.user.application.mapper;

import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.domain.model.UserModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T14:25:26-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class IUserRequestMapperImpl implements IUserRequestMapper {

    @Override
    public UserModel toUser(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setFirstName( userRequestDto.getFirstName() );
        userModel.setLastName( userRequestDto.getLastName() );
        userModel.setIdentityDocument( userRequestDto.getIdentityDocument() );
        userModel.setPhoneNumber( userRequestDto.getPhoneNumber() );
        userModel.setBirthDate( userRequestDto.getBirthDate() );
        userModel.setEmail( userRequestDto.getEmail() );
        userModel.setPassword( userRequestDto.getPassword() );
        userModel.setRol( userRequestDto.getRol() );

        return userModel;
    }
}

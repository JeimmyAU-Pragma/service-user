package com.pragma.user.application.mapper;

import com.pragma.user.application.dto.request.RoleDto;
import com.pragma.user.application.dto.response.UserResponseDto;
import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
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
public class IUserResponseMapperImpl implements IUserResponseMapper {

    @Override
    public UserResponseDto toResponse(UserModel userModel) {
        if ( userModel == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        if ( userModel.getId() != null ) {
            userResponseDto.setId( String.valueOf( userModel.getId() ) );
        }
        userResponseDto.setFirstName( userModel.getFirstName() );
        userResponseDto.setLastName( userModel.getLastName() );
        userResponseDto.setIdentityDocument( userModel.getIdentityDocument() );
        userResponseDto.setPhoneNumber( userModel.getPhoneNumber() );
        userResponseDto.setBirthDate( userModel.getBirthDate() );
        userResponseDto.setEmail( userModel.getEmail() );
        userResponseDto.setRole( roleModelToRoleDto( userModel.getRole() ) );

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> toResponseList(List<UserModel> allUsers) {
        if ( allUsers == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( allUsers.size() );
        for ( UserModel userModel : allUsers ) {
            list.add( toResponse( userModel ) );
        }

        return list;
    }

    protected RoleDto roleModelToRoleDto(RoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( roleModel.getId() );
        roleDto.setName( roleModel.getName() );
        roleDto.setDescription( roleModel.getDescription() );

        return roleDto;
    }
}

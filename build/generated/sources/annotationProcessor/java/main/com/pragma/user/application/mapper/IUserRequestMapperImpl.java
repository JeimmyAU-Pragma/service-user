package com.pragma.user.application.mapper;

import com.pragma.user.application.dto.request.EmployeeRequestDto;
import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.domain.model.RoleModel;
import com.pragma.user.domain.model.UserModel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-12T06:35:00-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class IUserRequestMapperImpl implements IUserRequestMapper {

    @Override
    public UserModel toUser(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String identityDocument = null;
        String phoneNumber = null;
        LocalDate birthDate = null;
        String email = null;
        String password = null;

        firstName = userRequestDto.getFirstName();
        lastName = userRequestDto.getLastName();
        identityDocument = userRequestDto.getIdentityDocument();
        phoneNumber = userRequestDto.getPhoneNumber();
        birthDate = userRequestDto.getBirthDate();
        email = userRequestDto.getEmail();
        password = userRequestDto.getPassword();

        Long id = null;
        RoleModel role = null;

        UserModel userModel = new UserModel( id, firstName, lastName, identityDocument, phoneNumber, birthDate, email, password, role );

        return userModel;
    }

    @Override
    public UserModel toEmployee(EmployeeRequestDto employeeRequestDto) {
        if ( employeeRequestDto == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String identityDocument = null;
        String phoneNumber = null;
        String email = null;
        String password = null;

        firstName = employeeRequestDto.getFirstName();
        lastName = employeeRequestDto.getLastName();
        identityDocument = employeeRequestDto.getIdentityDocument();
        phoneNumber = employeeRequestDto.getPhoneNumber();
        email = employeeRequestDto.getEmail();
        password = employeeRequestDto.getPassword();

        Long id = null;
        LocalDate birthDate = null;
        RoleModel role = null;

        UserModel userModel = new UserModel( id, firstName, lastName, identityDocument, phoneNumber, birthDate, email, password, role );

        return userModel;
    }
}

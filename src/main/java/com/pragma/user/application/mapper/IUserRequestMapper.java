package com.pragma.user.application.mapper;

import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    UserModel toUser(UserRequestDto userRequestDto);
}

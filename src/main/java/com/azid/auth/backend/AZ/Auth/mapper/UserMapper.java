package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.AuthResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.UserDto;
import com.azid.auth.backend.AZ.Auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);
    UserDto toDto(User user);

    @Mapping(target = "token", expression = "java(token)")
    AuthResponseDto toDto(User user, String token);
}

package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.PolicyDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    Policy toEntity(PolicyDto dto);
    PolicyDto toDto(Policy policy);
}

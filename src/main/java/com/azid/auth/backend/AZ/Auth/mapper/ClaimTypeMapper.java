package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.ClaimTypeDto;
import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClaimTypeMapper {

    ClaimType toEntity(ClaimTypeDto dto);
    ClaimTypeDto toDto(ClaimType claimType);
}

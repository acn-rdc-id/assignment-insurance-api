package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.model.Plan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    Plan toEntity(PlanRequestDto dto);
    PlanResponseDto toDto(Plan plan); // Corrected mapping
}

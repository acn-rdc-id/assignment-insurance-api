package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.model.Claim;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    Claim toEntity(ClaimDto dto);
    ClaimDto toDto(Claim claim);

    QuotationApplicationResponseDto toResponseDTO(QuotationApplication application);
    @Mapping(source = "claimId", target = "claimID")
    ClaimResponseDto claimToClaimResponseDTO(Claim claim);

}
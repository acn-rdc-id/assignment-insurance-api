package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.BeneficiaryRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyDto;
import com.azid.auth.backend.AZ.Auth.model.Beneficiary;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeneficiaryMapper {
    Beneficiary toEntity(BeneficiaryRequestDto dto);
    BeneficiaryRequestDto toDto(Beneficiary beneficiary);
    PolicyDto policyToPolicyResponseDTO(Policy policy);
}
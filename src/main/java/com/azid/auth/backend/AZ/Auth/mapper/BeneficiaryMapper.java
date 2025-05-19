package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.BeneficiaryDto;
import com.azid.auth.backend.AZ.Auth.model.Beneficiary;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface BeneficiaryMapper {
    Beneficiary toEntity(BeneficiaryDto dto);
    BeneficiaryDto toDto(Beneficiary beneficiary);

}

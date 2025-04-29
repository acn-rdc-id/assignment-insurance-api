package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.PolicyDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationDeclarationResponseDto;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationApplicationMapper {

    QuotationApplication toEntity(QuotationApplicationDto dto);
    QuotationDeclarationResponseDto toDto(QuotationApplication quotationApplication);
}

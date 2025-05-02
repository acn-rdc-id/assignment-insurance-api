package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationResponseDto;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationApplicationMapper {

    QuotationApplication toEntity(QuotationApplicationDto dto);
    QuotationApplicationDto toDto(QuotationApplication quotationApplication);
    QuotationApplicationResponseDto toResponseDto(QuotationApplication quotationApplication);
}

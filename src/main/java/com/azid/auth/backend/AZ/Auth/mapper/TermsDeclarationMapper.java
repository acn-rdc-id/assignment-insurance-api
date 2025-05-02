package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.model.TermsDeclaration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermsDeclarationMapper {

    TermsDeclaration toEntity(TermsDeclarationDto dto);
    TermsDeclarationDto toDto(TermsDeclaration termsDeclaration);
}

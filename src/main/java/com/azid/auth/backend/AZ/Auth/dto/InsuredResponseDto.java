package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.TermsDeclaration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuredResponseDto {
    @JsonIgnoreProperties
    private Long id;
    private String title;
    private String fullName;
    private String gender;
    private String nationality;
    private String identificationNo;
    private String countryOfBirth;
    private String phoneNo;
    private String email;
    private Date dateOfBirth;
    private boolean isSmoker;
    private Integer cigarettesNo;
    private String occupation;
    private String purposeOfTransaction;
    private PolicyDto policyDto;
    private TermsDeclarationDto termsDeclarationDto;
}

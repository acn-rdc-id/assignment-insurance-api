package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.TermsDeclaration;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuredDto {

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
    private Policy policy;
    private TermsDeclarationDto termsDeclarationDto;
}

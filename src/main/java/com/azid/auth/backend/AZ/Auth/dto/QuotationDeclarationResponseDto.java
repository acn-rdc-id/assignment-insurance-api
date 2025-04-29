package com.azid.auth.backend.AZ.Auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuotationDeclarationResponseDto {

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
    private Date paymentDate;
    private BigDecimal paymentAmount;
    private String paymentStatus;
    private Integer paymentDuration;
    private String planName;
    private BigDecimal coverageAmount;
    private BigDecimal basePremium;
    private Integer planDuration;
}

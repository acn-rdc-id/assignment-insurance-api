package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuotationApplicationResponseDto {
    public Long id;
    public String fullName;
    public String gender;
    public String nationality;
    public String identificationNo;
    public String countryOfBirth;
    public String phoneNo;
    public String email;
    public Date dateOfBirth;
    public boolean isSmoker;
    public Integer cigarettesNo;
    public String occupation;
    public String purposeOfTransaction;
    public String applicationStatus;
    public PlanInfoDto planResponseDto;
}

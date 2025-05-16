package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Plan;
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
    public String countryCode;
    public String countryOfBirth;
    public String phoneNo;
    public String email;
    public String title;
    public Date dateOfBirth;
    public boolean isSmoker;
    public Integer cigarettesNo;
    public String occupation;
    public String purposeOfTransaction;
    public String applicationStatus;
    public Plan plan;
    public PlanInfoDto planResponseDto;
}
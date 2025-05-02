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
public class PersonDto {
    public String userId;
    public String gender;
    public Date dateOfBirth;
    public Integer age;
    public String title;
    public String fullName;
    public String nationality;
    public String identificationNo;
    public String otherId;
    public boolean isUsPerson;
    public String countryOfBirth;
    public boolean isSmoker;
    public Integer cigarettesNo;
    public String countryCode;
    public String phoneNo;
    public String occupation;
    public String email;
    public String purposeOfTransaction;
}
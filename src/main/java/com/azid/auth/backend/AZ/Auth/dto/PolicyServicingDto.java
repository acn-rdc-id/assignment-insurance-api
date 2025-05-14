package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyServicingDto {
    private String fullName;
    private String nationality;
    private String countryOfBirth;
    private String phoneNo;
    private String email;
    private String occupation;
}

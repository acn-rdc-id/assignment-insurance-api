package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyServicingDto {
    private String title;
    private String fullName;
    private String countryCode;
    private String phoneNo;
    private String email;
}

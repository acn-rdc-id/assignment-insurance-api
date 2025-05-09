package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimDto {

    private Long claimId;
    private UserDto userDto;
    private ClaimTypeDto claimType;
    private PolicyDto policy;
    private Date claim_date;
    private String claimStatus;
}

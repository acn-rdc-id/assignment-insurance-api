package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import jakarta.persistence.*;
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

    private Long id;
    private PolicyDto policy;
    private String claimType;
    private Date claim_date;
    private String claimStatus;
}

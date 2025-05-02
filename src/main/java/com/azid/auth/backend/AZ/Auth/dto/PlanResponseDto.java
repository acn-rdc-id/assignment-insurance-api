package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanResponseDto {
    private String referenceNumber;
    private String gender;
    private String dateOfBirth;
    private int ageNearestBirthday;
    private List<PlanDetailsDto> plans;
}

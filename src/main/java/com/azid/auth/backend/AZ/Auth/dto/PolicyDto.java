package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyDto {

    private Long id;
    private String policyNo;
    private Date startDate;
    private Date endDate;
    private PlanDetailsDto planDetailsDto;
    private UserDto userDto;
    private InsuredDto insuredDto;
    private PaymentDto paymentDto;
    private List<ClaimDto> claimDtoList;
    private String status;

}

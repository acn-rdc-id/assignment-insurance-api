package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailsDto {
    private Long paymentId;
    private Date paymentDate;
    private BigDecimal paymentAmount;
    private String paymentStatus;
}

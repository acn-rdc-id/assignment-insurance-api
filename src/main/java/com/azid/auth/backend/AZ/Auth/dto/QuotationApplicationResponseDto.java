package com.azid.auth.backend.AZ.Auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuotationApplicationResponseDto {
    public Long quotationId;
    public String fullName;
    public String email;
    public String gender;
    public String planName;
    public BigDecimal basePremium;
    public Integer duration;
    public String applicationStatus;
}

package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull(message = "Quotation ID is required.")
    private Long quotationId;

    @DecimalMin(value = "0.01", inclusive = true, message = "Payment amount must be greater than 0.")
    private BigDecimal paymentAmount;

    private PaymentStatus paymentStatus;
}

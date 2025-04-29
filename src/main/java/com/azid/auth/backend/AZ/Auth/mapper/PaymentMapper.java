package com.azid.auth.backend.AZ.Auth.mapper;

import com.azid.auth.backend.AZ.Auth.dto.PaymentDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentDto dto);
    PaymentResponseDto toDto(Payment payment);
}

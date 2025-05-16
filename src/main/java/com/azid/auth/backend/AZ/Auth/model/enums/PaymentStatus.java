package com.azid.auth.backend.AZ.Auth.model.enums;

import com.azid.auth.backend.AZ.Auth.exceptions.BadRequestException;

public enum PaymentStatus {
    SUCCESS,
    FAILED;

    public static PaymentStatus fromString(String status) {
        try {
            return PaymentStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BadRequestException("Invalid payment status: " + status);
        }
    }
}
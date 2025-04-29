package com.azid.auth.backend.AZ.Auth.exceptions;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonPropertyOrder({"status", "code", "message", "timestamp", "errorDetails"})
public class ErrorResponse<T> {
    private String status;
    private int code;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, T> errorDetails;

    public ErrorResponse(String status, int code, String message, Map<String, T> errorDetails) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errorDetails = errorDetails;
    }
}


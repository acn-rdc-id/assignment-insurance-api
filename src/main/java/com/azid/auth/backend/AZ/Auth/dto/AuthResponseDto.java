package com.azid.auth.backend.AZ.Auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String token;
    private String email;
    private String username;
    private String userId;
}

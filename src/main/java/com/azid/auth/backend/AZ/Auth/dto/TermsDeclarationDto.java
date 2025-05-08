package com.azid.auth.backend.AZ.Auth.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

public class TermsDeclarationDto {

    private Long id;
    private String termsHtml;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

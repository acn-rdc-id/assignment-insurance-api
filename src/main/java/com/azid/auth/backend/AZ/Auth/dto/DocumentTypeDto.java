package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeDto {
    private long documentTypeId;
    private String documentTypeName;
    private ClaimType claimType;
    private boolean isRequired;
}

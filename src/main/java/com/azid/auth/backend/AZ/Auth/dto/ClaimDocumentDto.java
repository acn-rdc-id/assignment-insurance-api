package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Claim;
import com.azid.auth.backend.AZ.Auth.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimDocumentDto {
    private Long claimDocumentId;
    private Claim claim;
    private DocumentType documentType;
    private String documentUrl;
    private Timestamp documentUpload;
}

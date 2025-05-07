package com.azid.auth.backend.AZ.Auth.model;

import com.sun.tools.attach.AgentInitializationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id")
    private long documentTypeId;

    @Column(name="document_type_name")
    private String documentTypeName;

    @ManyToOne
    @JoinColumn(name = "claim_type_id")
    private ClaimType claimType;

    @Column(name="document_type_is_required")
    private boolean isRequired;

    public boolean getIsRequired() {
        return isRequired;
    }
}

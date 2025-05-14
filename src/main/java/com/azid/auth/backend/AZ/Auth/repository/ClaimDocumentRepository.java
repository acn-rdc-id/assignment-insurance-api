package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.ClaimDocument;
import com.azid.auth.backend.AZ.Auth.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument,Long> {

    @Query(value = "SELECT * FROM claim_document WHERE claim_id = ?1", nativeQuery = true)
    List<ClaimDocument> getClaimDocumentByClaimId(Long claimId);
}

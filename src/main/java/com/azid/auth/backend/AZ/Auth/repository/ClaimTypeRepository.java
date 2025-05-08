package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimTypeRepository extends JpaRepository<ClaimType,Long> {

    @Query(value = """
        SELECT ct.claim_type_id,ct.claim_type_name,ct.claim_type_description, dt.document_type_name
        FROM claim_type ct
        JOIN document_type dt ON ct.claim_type_id = dt.claim_type_id
    """, nativeQuery = true)
    List<Object[]> getClaimTypesWithDocuments();


    @Query(value = """
    SELECT ct.claim_type_id, ct.claim_type_name, ct.claim_type_description
    FROM claim_type ct
    JOIN claim c ON c.claim_type_id = ct.claim_type_id
    WHERE c.claim_id = :claimId
    """, nativeQuery = true)
    ClaimType getClaimTypeByClaimId(@Param("claimId") Long claimId);

}

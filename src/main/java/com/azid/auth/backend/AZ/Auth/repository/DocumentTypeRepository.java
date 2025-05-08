package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentType,Long> {

    @Query(value = "SELECT * FROM document_type WHERE claim_type_id = ?1", nativeQuery = true)
    List<DocumentType> findByClaimTypeId(String claimTypeId);

}

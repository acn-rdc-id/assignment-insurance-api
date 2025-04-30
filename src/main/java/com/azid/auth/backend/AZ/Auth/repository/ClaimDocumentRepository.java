package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.ClaimDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument,Long> {
}

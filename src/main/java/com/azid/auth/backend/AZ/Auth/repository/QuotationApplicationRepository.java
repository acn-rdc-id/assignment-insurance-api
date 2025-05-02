package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationApplicationRepository extends JpaRepository<QuotationApplication,Long> {
}

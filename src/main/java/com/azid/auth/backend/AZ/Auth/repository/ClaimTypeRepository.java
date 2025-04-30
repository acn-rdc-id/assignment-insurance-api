package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimTypeRepository extends JpaRepository<ClaimType,Long> {
}

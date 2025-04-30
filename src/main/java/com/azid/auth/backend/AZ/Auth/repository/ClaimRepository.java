package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim,Long> {
}

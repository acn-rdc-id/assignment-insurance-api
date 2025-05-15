package com.azid.auth.backend.AZ.Auth.repository;


import com.azid.auth.backend.AZ.Auth.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {
    List<Beneficiary> findByPolicyId(Long policyId);
}

package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}

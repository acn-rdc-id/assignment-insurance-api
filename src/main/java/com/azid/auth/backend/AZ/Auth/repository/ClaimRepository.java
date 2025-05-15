package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim,Long> {
    @Query("SELECT c FROM Claim c JOIN c.user u WHERE u.userId = :userId")
    List<Claim> findByUserUserId(@Param("userId") String userId);
}
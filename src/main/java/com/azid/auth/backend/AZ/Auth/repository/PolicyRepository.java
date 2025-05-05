package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy,Long> {
    @Query("SELECT p FROM Policy p JOIN p.user u WHERE u.userId = :userId")
    List<Policy> findByUserId(@Param("userId") String userId);
}

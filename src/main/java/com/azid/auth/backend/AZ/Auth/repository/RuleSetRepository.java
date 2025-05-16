package com.azid.auth.backend.AZ.Auth.repository;

import com.azid.auth.backend.AZ.Auth.model.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RuleSetRepository extends JpaRepository<RuleSet, Long> {

    @Query(value = """
        SELECT r.*
        FROM ruleset r
        JOIN plan p ON r.plan_id = p.plan_id
        WHERE r.plan_id = :planId
          AND r.gender = :gender
          AND r.payment_frequency = :paymentFrequency
          AND r.age_limit <= :age
          AND r.status = 'ACTIVE'
        ORDER BY r.age_limit DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<RuleSet> findMatchingRuleSet(
            @Param("planId") Long planId,
            @Param("gender") String gender,
            @Param("paymentFrequency") String paymentFrequency,
            @Param("age") Integer age
    );

}

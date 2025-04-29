package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ruleset")
public class RuleSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ruleset_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "rule_type")
    private String ruleType;

    @Column(name = "operator")
    private String operator;

    @Column(name = "age_limit")
    private Integer ageLimit;

    @Column(name = "coverage_amount")
    private Integer coverageAmount;

    @Column(name = "premium_amount")
    private Double premiumAmount;

    @Column(name = "gender")
    private String gender;

    @Column(name = "payment_frequency")
    private String paymentFrequency;

    @Column(name = "status")
    private String status;

}

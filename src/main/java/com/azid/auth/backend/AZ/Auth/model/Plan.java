package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "coverage_amount")
    private BigDecimal coverageAmount;

    @Column(name = "base_premium")
    private BigDecimal basePremium;

    @Column(name = "duration")
    private Integer duration;

    @OneToMany(mappedBy = "plan")
    private List<Policy> policyList;

    @OneToMany(mappedBy = "plan")
    private List<RuleSet> ruleSetList;

    @Column(name = "status")
    private String status;

}

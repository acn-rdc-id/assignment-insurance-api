package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double coverageAmount;

    @Column(name = "base_premium")
    private Double basePremium;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    private String status;

}

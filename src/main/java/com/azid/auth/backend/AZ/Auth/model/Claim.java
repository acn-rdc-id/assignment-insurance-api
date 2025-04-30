package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "claim_type_id")
    private ClaimType claimType;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    @Column(name = "claim_date")
    private Date claim_date;

    @Column(name = "claim_status")
    private String claimStatus;

}

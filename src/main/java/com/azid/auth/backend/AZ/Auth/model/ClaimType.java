package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim_type")
public class ClaimType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="claim_type_id")
    private long claimTypeId;

    @Column(name="claim_type_name")
    private String claimTypeName;

    @Column(name="claim_type_description")
    private String claimTypeDescription;

}

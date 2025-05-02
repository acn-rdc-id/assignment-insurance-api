package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "beneficiary")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beneficiary_id")
    private Long id;

    @OneToOne
    private Policy policy;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "relationship_to_insured")
    private String relationshipToInsured;
}

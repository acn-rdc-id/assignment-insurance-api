package com.azid.auth.backend.AZ.Auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quotation_application")
public class QuotationApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_id")
    private Long id;

    // Personal info
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "identification_no")
    private String identificationNo;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "is_smoker")
    private boolean isSmoker;

    @Column(name = "cigarettes_no")
    private Integer cigarettesNo;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "purpose_of_transaction")
    private String purposeOfTransaction;

    @Column(name = "application_status")
    private String applicationStatus; // PENDING, SUCCESS, FAILED

    // Relations
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    // Plan info
    @Column(name = "reference_number")
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "quotationApplication")
    private Payment payment;

}

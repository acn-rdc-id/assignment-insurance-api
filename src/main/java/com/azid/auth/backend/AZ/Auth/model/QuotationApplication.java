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
@Table(name = "quotation_application")
public class QuotationApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_id")
    private Long id;

    @Column(name = "title")
    private String title;

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

    @Column(name = "phoneNo")
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


    // payment details
    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_duration")
    private Integer paymentDuration;

    // plan details
    @Column(name = "plan_name")
    private String planName;

    @Column(name = "coverage_amount")
    private BigDecimal coverageAmount;

    @Column(name = "base_premium")
    private BigDecimal basePremium;

    @Column(name = "plan_duration")
    private Integer planDuration;



}

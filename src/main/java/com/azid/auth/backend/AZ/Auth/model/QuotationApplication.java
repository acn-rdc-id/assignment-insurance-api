package com.azid.auth.backend.AZ.Auth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "other_id")
    private String otherId;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "title")
    private String title;

    @Column(name = "is_smoker")
    private boolean isSmoker;

    @Column(name = "is_us_person")
    private boolean isUsPerson;

    @Column(name = "cigarettes_no")
    private Integer cigarettesNo;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "purpose_of_transaction")
    private String purposeOfTransaction;

    @Column(name = "application_status")
    private String applicationStatus; // PENDING, SUCCESS, FAILED

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "premium_amount")
    private BigDecimal premiumAmount;

    @Column(name = "premium_mode")
    private String premiumMode;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "quotationApplication")
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = "quotationApplication")
    @JsonBackReference //do not serialize (used to break loop)
    private Policy policy;

}

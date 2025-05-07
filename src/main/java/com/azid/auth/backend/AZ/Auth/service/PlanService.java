package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PlanDetailsDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.exceptions.BadRequestException;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.PlanMapper;
import com.azid.auth.backend.AZ.Auth.model.Plan;
import com.azid.auth.backend.AZ.Auth.model.RuleSet;
import com.azid.auth.backend.AZ.Auth.model.enums.GenderEnum;
import com.azid.auth.backend.AZ.Auth.repository.PlanRepository;
import com.azid.auth.backend.AZ.Auth.repository.RuleSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlanService {
    @Autowired
    private RuleSetRepository ruleSetRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    public Plan getPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
    }

    public PlanResponseDto generatePlan(PlanRequestDto request) {
        log.info("start [generatePlan] for gender: {}, DOB: {}", request.getGender(), request.getDateOfBirth());

        try {
            LocalDate dob = request.getDateOfBirth();
            LocalDate today = LocalDate.now();

            GenderEnum gender = request.getGender();
            String genderCode = gender.getCode();

            int age = Period.between(dob, today).getYears();
            if (today.getDayOfYear() < dob.withYear(today.getYear()).getDayOfYear()) {
                age += 1;
            }

            List<Plan> plans = planRepository.findAll();
            if (plans.isEmpty()) {
                throw new ResourceNotFoundException("No plans available for quotation.");
            }

            List<PlanDetailsDto> planDetails = new ArrayList<>();
            for (Plan plan : plans) {
                Double monthlyPremium = findPremium(plan.getId(), genderCode, "MONTHLY", age);
                Double yearlyPremium = findPremium(plan.getId(), genderCode, "YEARLY", age);

                if (monthlyPremium != null && yearlyPremium != null) {
                    PlanDetailsDto detail = new PlanDetailsDto();
                    detail.setId(plan.getId());
                    detail.setPlanName(plan.getPlanName());
                    detail.setSumAssured(plan.getCoverageAmount());
                    detail.setMonthlyPremium(monthlyPremium);
                    detail.setYearlyPremium(yearlyPremium);
                    detail.setCoverageTerm(plan.getDuration() + " years");
                    planDetails.add(detail);
                }
            }

            if (planDetails.isEmpty()) {
                throw new ResourceNotFoundException("No matching plans found for the provided information.");
            }

            PlanResponseDto response = new PlanResponseDto();
            response.setReferenceNumber(generateReferenceNumber());
            response.setGender(gender.name().toLowerCase());
            response.setDateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            response.setAgeNearestBirthday(age);
            response.setPlans(planDetails);

            log.info("Plan generated successfully. referenceNumber: {}", response.getReferenceNumber());
            return response;

        } catch (Exception e) {
            log.error("Unexpected error during [generatePlan]", e);
            throw new RuntimeException("Unexpected error occurred while generating plans.");
        }
    }

    public Double findPremium(Long planId, String gender, String frequency, int age) {
        return ruleSetRepository
                .findMatchingRuleSet(planId, gender, frequency, age)
                .map(RuleSet::getPremiumAmount)
                .orElse(null);
    }

    private String generateReferenceNumber() {
        return "Q" + (int) (Math.random() * 1000000000);
    }


}

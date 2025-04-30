package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PlanDetailsDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.PlanMapper;
import com.azid.auth.backend.AZ.Auth.model.Plan;
import com.azid.auth.backend.AZ.Auth.model.RuleSet;
import com.azid.auth.backend.AZ.Auth.repository.PlanRepository;
import com.azid.auth.backend.AZ.Auth.repository.RuleSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {
    @Autowired
    private RuleSetRepository ruleSetRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    public Plan getPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    public PlanResponseDto generatePlan(PlanRequestDto request) {
        LocalDate dob = request.getDateOfBirth();
        LocalDate today = LocalDate.now();

        // TODO: add validation for gender request
        //String gender = request.getGender().equalsIgnoreCase("male") ? "M" : "F";

        String genderInput = request.getGender();
        if (genderInput == null || (!genderInput.equalsIgnoreCase("male") && !genderInput.equalsIgnoreCase("female"))) {
            throw new IllegalArgumentException("Invalid gender. Please provide 'male' or 'female'.");
        }
        String gender = genderInput.equalsIgnoreCase("male") ? "M" : "F";

        int age = Period.between(dob, today).getYears();
        if (today.getDayOfYear() < dob.withYear(today.getYear()).getDayOfYear()) {
            age += 1;
        }

        List<Plan> plans = planRepository.findAll();
        List<PlanDetailsDto> planDetails = new ArrayList<>();

        for (Plan plan : plans) {
            Double monthlyPremium = findPremium(plan.getId(), gender, "MONTHLY", age);
            Double yearlyPremium = findPremium(plan.getId(), gender, "YEARLY", age);

            if (monthlyPremium != null && yearlyPremium != null) {
                PlanDetailsDto detail = new PlanDetailsDto();
                detail.setPlanName(plan.getPlanName());
                detail.setSumAssured(plan.getCoverageAmount());
                detail.setMonthlyPremium(monthlyPremium);
                detail.setYearlyPremium(yearlyPremium);
                detail.setCoverageTerm(plan.getDuration().toString().concat(" years"));
                planDetails.add(detail);
            }
        }

        // TODO: update if can use mapper
        PlanResponseDto response = new PlanResponseDto();
        response.setReferenceNumber(generateReferenceNumber());
        response.setGender(request.getGender());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.setDateOfBirth(dob.format(dateFormatter));
        response.setAgeNearestBirthday(age);
        response.setPlans(planDetails);

        return response;
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

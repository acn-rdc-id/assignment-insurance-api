package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.PlanMapper;
import com.azid.auth.backend.AZ.Auth.model.Plan;
import com.azid.auth.backend.AZ.Auth.model.RuleSet;
import com.azid.auth.backend.AZ.Auth.model.enums.GenderEnum;
import com.azid.auth.backend.AZ.Auth.repository.PlanRepository;
import com.azid.auth.backend.AZ.Auth.repository.RuleSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {
    @InjectMocks
    PlanService planService;

    @Mock
    RuleSetRepository ruleSetRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    PlanMapper planMapper;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(planService, "ruleSetRepository", ruleSetRepository);
        ReflectionTestUtils.setField(planService, "planRepository", planRepository);
        ReflectionTestUtils.setField(planService, "planMapper", planMapper);
    }

    @Test
    void getPlan_validResponse() {
        Plan plan = new Plan();

        when(planRepository.findById(any())).thenReturn(Optional.of(plan));

        Plan result = planService.getPlan(123L);
        assertNotNull(result);
    }

    @Test
    void generatePlan_validResponse() {
        Plan plan = new Plan();
        plan.setId(123L);
        plan.setPlanName("planName");
        plan.setCoverageAmount(10.0);
        plan.setDuration(1);

        RuleSet ruleSet = new RuleSet();
        ruleSet.setPremiumAmount(100.0);

        when(planRepository.findAll()).thenReturn(List.of(plan));
        when(ruleSetRepository.findMatchingRuleSet(any(), any(), any(), any())).thenReturn(Optional.of(ruleSet));

        PlanRequestDto planRequestDto = new PlanRequestDto();
        planRequestDto.setGender(GenderEnum.MALE);
        planRequestDto.setDateOfBirth(LocalDate.now());

        PlanResponseDto result = planService.generatePlan(planRequestDto);
        assertNotNull(result);
    }
}

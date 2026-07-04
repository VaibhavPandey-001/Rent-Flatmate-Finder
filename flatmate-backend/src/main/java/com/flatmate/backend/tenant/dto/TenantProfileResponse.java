package com.flatmate.backend.tenant.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TenantProfileResponse {

    private String preferredLocation;

    private Double minBudget;

    private Double maxBudget;

    private LocalDate moveInDate;
}
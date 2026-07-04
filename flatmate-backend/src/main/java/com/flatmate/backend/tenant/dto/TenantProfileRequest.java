package com.flatmate.backend.tenant.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TenantProfileRequest {

    private String preferredLocation;

    private Double minBudget;

    private Double maxBudget;

    private LocalDate moveInDate;
}
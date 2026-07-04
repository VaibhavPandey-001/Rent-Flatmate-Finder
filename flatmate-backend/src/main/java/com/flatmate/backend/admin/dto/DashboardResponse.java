package com.flatmate.backend.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {

    private long users;

    private long owners;

    private long tenants;

    private long listings;

    private long activeListings;

    private long interests;

    private long messages;

}
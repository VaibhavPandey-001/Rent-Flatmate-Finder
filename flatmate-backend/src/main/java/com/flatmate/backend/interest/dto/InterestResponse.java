package com.flatmate.backend.interest.dto;

import com.flatmate.backend.interest.entity.InterestStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterestResponse {

    private Long id;

    private String tenantName;

    private String tenantEmail;

    private Long listingId;

    private String listingTitle;

    private InterestStatus status;

}
package com.flatmate.backend.match.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {

    private Long listingId;

    private String title;

    private String location;

    private Double rent;

    private Integer score;

    private String explanation;

}
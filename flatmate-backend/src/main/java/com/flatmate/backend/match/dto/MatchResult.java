package com.flatmate.backend.match.dto;

import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult {

    private Integer score;

    private String explanation;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
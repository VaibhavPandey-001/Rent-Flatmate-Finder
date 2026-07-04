package com.flatmate.backend.tenant.entity;

import com.flatmate.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String preferredLocation;

    private Double minBudget;

    private Double maxBudget;

    private LocalDate moveInDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
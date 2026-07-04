package com.flatmate.backend.match.entity;

import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.tenant.entity.TenantProfile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"tenant_id", "listing_id"}
        )
)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @Column(length = 1500)
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantProfile tenant;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;
}
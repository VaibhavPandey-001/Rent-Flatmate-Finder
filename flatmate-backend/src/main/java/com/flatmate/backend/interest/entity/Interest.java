package com.flatmate.backend.interest.entity;

import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "tenant_id",
                        "listing_id"
                }
        )
)
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Enumerated(EnumType.STRING)
    private InterestStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
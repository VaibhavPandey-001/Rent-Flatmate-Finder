package com.flatmate.backend.listing.entity;

import com.flatmate.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String location;

    private Double rent;

    private LocalDate availableFrom;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private FurnishingType furnishing;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @ElementCollection
    @CollectionTable(name = "listing_photos", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "photo_url")
    private List<String> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
}
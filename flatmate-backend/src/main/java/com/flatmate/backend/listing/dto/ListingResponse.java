package com.flatmate.backend.listing.dto;

import com.flatmate.backend.listing.entity.FurnishingType;
import com.flatmate.backend.listing.entity.ListingStatus;
import com.flatmate.backend.listing.entity.RoomType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ListingResponse {

    private Long id;

    private String title;

    private String description;

    private String location;

    private Double rent;

    private LocalDate availableFrom;

    private RoomType roomType;

    private FurnishingType furnishing;

    private List<String> photos;

    private String owner;

    private ListingStatus status;
}
package com.flatmate.backend.listing.dto;

import com.flatmate.backend.listing.entity.FurnishingType;
import com.flatmate.backend.listing.entity.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListingRequest {

    private String title;

    private String description;

    private String location;

    private Double rent;

    private LocalDate availableFrom;

    private RoomType roomType;

    private FurnishingType furnishing;

    private List<String> photos;
}
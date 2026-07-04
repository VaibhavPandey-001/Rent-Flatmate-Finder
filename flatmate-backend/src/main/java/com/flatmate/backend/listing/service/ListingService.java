package com.flatmate.backend.listing.service;

import com.flatmate.backend.exception.BadRequestException;
import com.flatmate.backend.exception.ResourceNotFoundException;
import com.flatmate.backend.listing.dto.ListingRequest;
import com.flatmate.backend.listing.dto.ListingResponse;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.listing.entity.ListingStatus;
import com.flatmate.backend.listing.repository.ListingRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingResponse create(ListingRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Listing listing = Listing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .rent(request.getRent())
                .availableFrom(request.getAvailableFrom())
                .roomType(request.getRoomType())
                .furnishing(request.getFurnishing())
                .photos(request.getPhotos())
                .status(ListingStatus.AVAILABLE)
                .owner(owner)
                .build();

        return toResponse(listingRepository.save(listing));
    }

    public List<ListingResponse> getAll() {

        return listingRepository.findByStatus(ListingStatus.AVAILABLE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ListingResponse> myListings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return listingRepository.findByOwner(owner)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void markFilled(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Listing listing = listingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        if (!listing.getOwner().getEmail().equals(email)) {
            throw new BadRequestException("Access denied");
        }

        listing.setStatus(ListingStatus.FILLED);

        listingRepository.save(listing);
    }

    private ListingResponse toResponse(Listing listing) {

        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .location(listing.getLocation())
                .rent(listing.getRent())
                .availableFrom(listing.getAvailableFrom())
                .roomType(listing.getRoomType())
                .furnishing(listing.getFurnishing())
                .photos(listing.getPhotos())
                .owner(listing.getOwner().getName())
                .status(listing.getStatus())
                .build();
    }


}
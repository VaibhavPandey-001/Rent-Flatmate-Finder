package com.flatmate.backend.listing.controller;

import com.flatmate.backend.listing.dto.ListingRequest;
import com.flatmate.backend.listing.dto.ListingResponse;
import com.flatmate.backend.listing.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;


    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ListingResponse create(@RequestBody ListingRequest request) {
        return listingService.create(request);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER','TENANT','ADMIN')")
    public List<ListingResponse> getAll() {
        return listingService.getAll();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('OWNER')")
    public List<ListingResponse> myListings() {
        return listingService.myListings();
    }

    @PatchMapping("/{id}/filled")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> markFilled(@PathVariable Long id) {
        listingService.markFilled(id);
        return ResponseEntity.ok("Listing marked as filled");
    }

}
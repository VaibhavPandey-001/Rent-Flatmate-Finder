package com.flatmate.backend.admin.controller;

import com.flatmate.backend.admin.dto.DashboardResponse;
import com.flatmate.backend.admin.service.DashboardService;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.listing.repository.ListingRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {

        return dashboardService.dashboard();

    }

    @GetMapping("/users")
    public List<User> users() {

        return userRepository.findAll();

    }

    @GetMapping("/listings")
    public List<Listing> listings() {

        return listingRepository.findAll();

    }

}
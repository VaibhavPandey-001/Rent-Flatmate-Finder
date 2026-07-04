package com.flatmate.backend.tenant.service;

import com.flatmate.backend.exception.ResourceNotFoundException;
import com.flatmate.backend.match.repository.MatchRepository;
import com.flatmate.backend.tenant.dto.TenantProfileRequest;
import com.flatmate.backend.tenant.dto.TenantProfileResponse;
import com.flatmate.backend.tenant.entity.TenantProfile;
import com.flatmate.backend.tenant.repository.TenantProfileRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantProfileRepository tenantProfileRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public TenantProfileResponse save(TenantProfileRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TenantProfile profile = tenantProfileRepository
                .findByUser(user)
                .orElse(new TenantProfile());

        profile.setPreferredLocation(request.getPreferredLocation());
        profile.setMinBudget(request.getMinBudget());
        profile.setMaxBudget(request.getMaxBudget());
        profile.setMoveInDate(request.getMoveInDate());
        profile.setUser(user);

        profile = tenantProfileRepository.save(profile);

        matchRepository.deleteByTenant(profile);

        return TenantProfileResponse.builder()
                .preferredLocation(profile.getPreferredLocation())
                .minBudget(profile.getMinBudget())
                .maxBudget(profile.getMaxBudget())
                .moveInDate(profile.getMoveInDate())
                .build();
    }

    public TenantProfileResponse getProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TenantProfile profile = tenantProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        return TenantProfileResponse.builder()
                .preferredLocation(profile.getPreferredLocation())
                .minBudget(profile.getMinBudget())
                .maxBudget(profile.getMaxBudget())
                .moveInDate(profile.getMoveInDate())
                .build();
    }
}
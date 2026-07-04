package com.flatmate.backend.tenant.controller;

import com.flatmate.backend.tenant.dto.TenantProfileRequest;
import com.flatmate.backend.tenant.dto.TenantProfileResponse;
import com.flatmate.backend.tenant.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/profile")
    @PreAuthorize("hasRole('TENANT')")
    public TenantProfileResponse saveProfile(
            @Valid @RequestBody TenantProfileRequest request) {

        return tenantService.save(request);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('TENANT')")
    public TenantProfileResponse getProfile() {

        return tenantService.getProfile();
    }
}
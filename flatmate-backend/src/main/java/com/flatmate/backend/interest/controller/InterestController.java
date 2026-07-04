package com.flatmate.backend.interest.controller;

import com.flatmate.backend.interest.dto.InterestResponse;
import com.flatmate.backend.interest.entity.InterestStatus;
import com.flatmate.backend.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping("/{listingId}")
    @PreAuthorize("hasRole('TENANT')")
    public void sendInterest(@PathVariable Long listingId) {

        interestService.sendInterest(listingId);

    }
    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public List<InterestResponse> ownerRequests() {

        return interestService.ownerRequests();

    }

    @PatchMapping("/{id}/accept")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> accept(@PathVariable Long id) {

        interestService.update(id, InterestStatus.ACCEPTED);

        return ResponseEntity.ok("Accepted");

    }

    @PatchMapping("/{id}/decline")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> decline(@PathVariable Long id) {

        interestService.update(id, InterestStatus.DECLINED);

        return ResponseEntity.ok("Declined");

    }


}
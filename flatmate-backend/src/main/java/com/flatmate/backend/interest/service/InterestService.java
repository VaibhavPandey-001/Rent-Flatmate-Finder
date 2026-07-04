package com.flatmate.backend.interest.service;

import com.flatmate.backend.email.EmailService;
import com.flatmate.backend.exception.BadRequestException;
import com.flatmate.backend.exception.ResourceNotFoundException;
import com.flatmate.backend.interest.dto.InterestResponse;
import com.flatmate.backend.interest.entity.Interest;
import com.flatmate.backend.interest.entity.InterestStatus;
import com.flatmate.backend.interest.repository.InterestRepository;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.listing.repository.ListingRepository;
import com.flatmate.backend.match.entity.Match;
import com.flatmate.backend.match.repository.MatchRepository;
import com.flatmate.backend.tenant.entity.TenantProfile;
import com.flatmate.backend.tenant.repository.TenantProfileRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final TenantProfileRepository tenantProfileRepository;
    private final MatchRepository matchRepository;
    private final EmailService emailService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    public void sendInterest(Long listingId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User tenant = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        interestRepository.findByTenantAndListing(tenant, listing)
                .ifPresent(i -> {
                    throw new BadRequestException("Interest already sent");
                });

        Interest interest = Interest.builder()
                .tenant(tenant)
                .listing(listing)
                .status(InterestStatus.PENDING)
                .build();

        interestRepository.save(interest);
        messagingTemplate.convertAndSend(
                "/topic/owner/" + listing.getOwner().getId(),
                "New interest received for listing: " + listing.getTitle()
        );
        TenantProfile profile = tenantProfileRepository.findByUser(tenant)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        Match match = matchRepository.findByTenantAndListing(profile, listing)
                .orElse(null);

        if (match != null && match.getScore() >= 80) {

            emailService.send(
                    listing.getOwner().getEmail(),
                    "High Compatibility Match",
                    tenant.getName() + " is interested in your listing.\nCompatibility Score : " + match.getScore()
            );
        }
    }

    public List<InterestResponse> ownerRequests() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return interestRepository.findByListingOwner(owner)
                .stream()
                .map(this::map)
                .toList();
    }

    public void update(Long id, InterestStatus status) {

        Interest interest = interestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!interest.getListing().getOwner().getId().equals(owner.getId())) {
            throw new BadRequestException("You cannot update this request");
        }

        interest.setStatus(status);

        interestRepository.save(interest);
        messagingTemplate.convertAndSend(
                "/topic/tenant/" + interest.getTenant().getId(),
                "Your request was " + status
        );
        emailService.send(
                interest.getTenant().getEmail(),
                "Interest Update",
                "Your request for '" +
                        interest.getListing().getTitle() +
                        "' has been " +
                        status.name().toLowerCase()
        );

    }

    private InterestResponse map(Interest interest) {

        return InterestResponse.builder()
                .id(interest.getId())
                .tenantName(interest.getTenant().getName())
                .tenantEmail(interest.getTenant().getEmail())
                .listingId(interest.getListing().getId())
                .listingTitle(interest.getListing().getTitle())
                .status(interest.getStatus())
                .build();

    }

}
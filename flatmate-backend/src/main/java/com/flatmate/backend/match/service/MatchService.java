package com.flatmate.backend.match.service;

import com.flatmate.backend.exception.ResourceNotFoundException;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.listing.entity.ListingStatus;
import com.flatmate.backend.listing.repository.ListingRepository;
import com.flatmate.backend.match.dto.MatchResponse;
import com.flatmate.backend.match.dto.MatchResult;
import com.flatmate.backend.match.entity.Match;
import com.flatmate.backend.match.repository.MatchRepository;
import com.flatmate.backend.tenant.entity.TenantProfile;
import com.flatmate.backend.tenant.repository.TenantProfileRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final ListingRepository listingRepository;
    private final TenantProfileRepository tenantProfileRepository;
    private final UserRepository userRepository;
    private final GroqService groqService;

    public List<MatchResponse> browse() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TenantProfile tenant = tenantProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant profile not found"));

        List<Listing> listings =
                listingRepository.findByStatusOrderByIdDesc(ListingStatus.AVAILABLE);

        List<MatchResponse> result = new ArrayList<>();

        for (Listing listing : listings) {

            Match match = matchRepository
                    .findByTenantAndListing(tenant, listing)
                    .orElse(null);

            if (match == null) {

                MatchResult ai = groqService.findMatch(tenant, listing);

                if (ai == null) {
                    ai = fallback(tenant, listing);
                }

                match = Match.builder()
                        .tenant(tenant)
                        .listing(listing)
                        .score(ai.getScore())
                        .explanation(ai.getExplanation())
                        .build();

                matchRepository.save(match);
            }

            result.add(
                    MatchResponse.builder()
                            .listingId(listing.getId())
                            .title(listing.getTitle())
                            .location(listing.getLocation())
                            .rent(listing.getRent())
                            .score(match.getScore())
                            .explanation(match.getExplanation())
                            .build()
            );
        }

        result.sort(
                Comparator.comparing(MatchResponse::getScore).reversed()
        );

        return result;
    }

    private MatchResult fallback(TenantProfile tenant, Listing listing) {

        int score = 0;

        if (listing.getLocation().equalsIgnoreCase(tenant.getPreferredLocation())) {
            score += 50;
        }

        if (listing.getRent() >= tenant.getMinBudget()
                && listing.getRent() <= tenant.getMaxBudget()) {
            score += 50;
        }

        return MatchResult.builder()
                .score(score)
                .explanation("Calculated using fallback rules.")
                .build();
    }

}
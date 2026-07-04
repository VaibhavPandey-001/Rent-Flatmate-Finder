package com.flatmate.backend.match.repository;

import com.flatmate.backend.match.entity.Match;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.tenant.entity.TenantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match,Long> {

    Optional<Match> findByTenantAndListing(
            TenantProfile tenant,
            Listing listing
    );

    List<Match> findByTenantOrderByScoreDesc(
            TenantProfile tenant
    );

    void deleteByTenant(TenantProfile tenant);
}
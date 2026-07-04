package com.flatmate.backend.interest.repository;

import com.flatmate.backend.interest.entity.Interest;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<Interest> findByTenantAndListing(
            User tenant,
            Listing listing
    );

    List<Interest> findByListingOwner(User owner);

    List<Interest> findByTenant(User tenant);
}
package com.flatmate.backend.listing.repository;

import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.listing.entity.ListingStatus;
import com.flatmate.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByStatus(ListingStatus status);

    List<Listing> findByLocationContainingIgnoreCaseAndStatus(
            String location,
            ListingStatus status
    );
    List<Listing> findByOwner(User owner);
    List<Listing> findByStatusOrderByIdDesc(ListingStatus status);
    long countByStatus(ListingStatus status);
}
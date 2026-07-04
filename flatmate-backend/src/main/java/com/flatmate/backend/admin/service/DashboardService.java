package com.flatmate.backend.admin.service;

import com.flatmate.backend.admin.dto.DashboardResponse;
import com.flatmate.backend.chat.repository.ChatRepository;
import com.flatmate.backend.interest.repository.InterestRepository;
import com.flatmate.backend.listing.entity.ListingStatus;
import com.flatmate.backend.listing.repository.ListingRepository;
import com.flatmate.backend.user.Role;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final InterestRepository interestRepository;
    private final ChatRepository chatRepository;

    public DashboardResponse dashboard() {

        List<User> users = userRepository.findAll();

        return DashboardResponse.builder()
                .users(users.size())
                .owners(users.stream().filter(u -> u.getRole() == Role.OWNER).count())
                .tenants(users.stream().filter(u -> u.getRole() == Role.TENANT).count())
                .listings(listingRepository.count())
                .activeListings(listingRepository.countByStatus(ListingStatus.AVAILABLE))
                .interests(interestRepository.count())
                .messages(chatRepository.count())
                .build();

    }

}
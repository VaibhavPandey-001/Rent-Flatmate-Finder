package com.flatmate.backend.tenant.repository;

import com.flatmate.backend.tenant.entity.TenantProfile;
import com.flatmate.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantProfileRepository extends JpaRepository<TenantProfile, Long> {

    Optional<TenantProfile> findByUser(User user);
}
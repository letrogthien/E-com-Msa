package com.letrogthien.user.repositories;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.entities.UserKyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KycRepository extends JpaRepository<UserKyc, UUID> {
    Optional<UserKyc> findTopByUserIdOrderByVersionDesc(UUID userId);

    List<UserKyc> findAllByVerificationStatus(Status verificationStatus);
    List<UserKyc> findAllByUserId(UUID userId);
}

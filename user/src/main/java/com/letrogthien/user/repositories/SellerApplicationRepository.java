package com.letrogthien.user.repositories;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.entities.SellerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SellerApplicationRepository extends JpaRepository<SellerApplication, UUID> {
    List<SellerApplication> findAllByApplicationStatus(Status applicationStatus);
    List<SellerApplication> findAllByUserIdAndApplicationStatus(UUID userId, Status applicationStatus);
    List<SellerApplication> findAllByUserId(UUID userId);
}

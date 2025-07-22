package com.letrogthien.user.repositories;

import com.letrogthien.user.entities.SellerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SellerRatingRepository extends JpaRepository<SellerRating, UUID> {
    // Custom query methods can be defined here if needed
    List<SellerRating> findAllBySellerId(UUID sellerId);

    List<SellerRating> findAllByBuyerId(UUID buyerId);

    boolean existsByBuyerIdAndTransactionId(UUID buyerId, UUID transactionId);

    @Query("SELECT AVG(sr.ratingScore) FROM SellerRating sr WHERE sr.seller.id = :sellerId")
    Double findAverageScoreBySellerId(@Param("sellerId") UUID sellerId);

}

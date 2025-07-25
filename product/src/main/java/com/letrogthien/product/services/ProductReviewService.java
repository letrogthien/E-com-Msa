
package com.letrogthien.product.services;

import com.letrogthien.product.dto.ProductReviewDto;
import com.letrogthien.product.request.ProductReviewRequest;
import com.letrogthien.product.responses.ApiResponse;

import java.util.List;
import java.util.UUID;


/**
 * Service interface for managing {@link ProductReview} entities.
 */
public interface ProductReviewService {

    /**
     * Retrieves all product reviews.
     *
     * @return ApiResponse containing a list of all product review DTOs
     */
    ApiResponse<List<ProductReviewDto>> getAllProductReviews();

    /**
     * Retrieves a product review by its ID.
     *
     * @param id the ID of the product review
     * @return ApiResponse containing the product review DTO with the specified ID, or error if not found
     */
    ApiResponse<ProductReviewDto> getProductReviewById(UUID id);

    /**
     * Saves a new product review.
     *
     * @param productReview the product review request to save
     * @return ApiResponse containing the saved product review DTO
     */
    ApiResponse<ProductReviewDto> saveProductReview(ProductReviewRequest productReview);

    /**
     * Updates an existing product review.
     *
     * @param productReview the product review request to update
     * @return ApiResponse containing the updated product review DTO
     */
    ApiResponse<ProductReviewDto> updateProductReview(ProductReviewRequest productReview);

    /**
     * Deletes a product review by its ID.
     *
     * @param id the ID of the product review to delete
     * @return ApiResponse indicating success or failure
     */
    ApiResponse<Void> deleteProductReview(UUID id);
}

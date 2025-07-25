
package com.letrogthien.product.services;

import java.util.List;
import java.util.UUID;

import com.letrogthien.product.dto.ProductImageDto;
import com.letrogthien.product.request.ProductImageRequest;
import com.letrogthien.product.responses.ApiResponse;

/**
 * Service interface for managing {@link ProductImage} entities.
 */
public interface ProductImageService {
    /**
     * Retrieves all product images.
     *
     * @return ApiResponse containing a list of all product image DTOs
     */
    ApiResponse<List<ProductImageDto>> getAllProductImages();

    /**
     * Retrieves a product image by its ID.
     *
     * @param id the ID of the product image
     * @return ApiResponse containing the product image DTO with the specified ID, or error if not found
     */
    ApiResponse<ProductImageDto> getProductImageById(UUID id);

    /**
     * Saves a new product image.
     *
     * @param productImage the product image request to save
     * @return ApiResponse containing the saved product image DTO
     */
    ApiResponse<ProductImageDto> saveProductImage(ProductImageRequest productImage);

    /**
     * Updates an existing product image.
     *
     * @param productImage the product image request to update
     * @return ApiResponse containing the updated product image DTO
     */
    ApiResponse<ProductImageDto> updateProductImage(ProductImageRequest productImage);

    /**
     * Deletes a product image by its ID.
     *
     * @param id the ID of the product image to delete
     * @return ApiResponse indicating success or failure
     */
    ApiResponse<Void> deleteProductImage(UUID id);

    /**
     * Finds product images by their associated product ID.
     *
     * @param productId the ID of the product
     * @return ApiResponse containing a list of product image DTOs associated with the specified product ID
     */
    ApiResponse<List<ProductImageDto>> findProductImagesByProductId(UUID productId);

}

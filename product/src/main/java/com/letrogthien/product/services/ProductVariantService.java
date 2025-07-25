
package com.letrogthien.product.services;

import com.letrogthien.product.dto.ProductVariantDto;
import com.letrogthien.product.request.ProductVariantRequest;
import com.letrogthien.product.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing {@link ProductVariant} entities.
 */
public interface ProductVariantService {
    /**
     * Retrieves all product variants.
     *
     * @return ApiResponse containing a list of all product variant DTOs
     */
    ApiResponse<List<ProductVariantDto>> getAllProductVariants();

    /**
     * Retrieves a product variant by its ID.
     *
     * @param id the ID of the product variant
     * @return ApiResponse containing the product variant DTO with the specified ID, or error if not found
     */
    ApiResponse<ProductVariantDto> getProductVariantById(UUID id);

    /**
     * Saves a new or existing product variant.
     *
     * @param productVariant the product variant request to save
     * @return ApiResponse containing the saved product variant DTO
     */
    ApiResponse<ProductVariantDto> saveProductVariant(ProductVariantRequest productVariant);

    /**
     * Updates an existing product variant.
     *
     * @param productVariant the product variant request to update
     * @return ApiResponse containing the updated product variant DTO
     */
    ApiResponse<ProductVariantDto> updateProductVariant(ProductVariantRequest productVariant);

    /**
     * Deletes a product variant by its ID.
     *
     * @param id the ID of the product variant to delete
     * @return ApiResponse indicating success or failure
     */
    ApiResponse<Void> deleteProductVariant(UUID id);

}

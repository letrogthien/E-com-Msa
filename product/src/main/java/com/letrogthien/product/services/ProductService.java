package com.letrogthien.product.services;

import com.letrogthien.product.dto.ProductDto;
import com.letrogthien.product.request.ProductRequest;
import com.letrogthien.product.responses.ApiResponse;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing {@link Product} entities.
 */
public interface ProductService {
    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    ApiResponse<List<ProductDto>> getAllProducts();

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the specified ID, or null if not found
     */
    ApiResponse<ProductDto> getProductById(UUID id);

    /**
     * Saves a new or existing product.
     *
     * @param product the product to save
     * @return the saved product
     */
    ApiResponse<ProductDto> saveProduct(ProductRequest productRq);
    /**
     * Updates an existing product.
     *
     * @param product the product to update
     * @return the updated product
     */
    ApiResponse<ProductDto> updateProduct(ProductRequest productRq);
    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    ApiResponse<ProductDto> deleteProduct(UUID id);
    /**
     * Finds products by their associated category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of products associated with the specified category ID
     */
    ApiResponse<List<ProductDto>> findProductsByCategoryId(UUID categoryId);
}

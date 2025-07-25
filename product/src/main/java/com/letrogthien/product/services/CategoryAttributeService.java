package com.letrogthien.product.services;

import java.util.List;
import java.util.UUID;

import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.entities.CategoryAttribute;
import com.letrogthien.product.responses.ApiResponse;


/**
 * Service interface for managing {@link CategoryAttribute} entities.
 */
public interface CategoryAttributeService {
    /**
     * Retrieves all category attributes.
     *
     * @return a list of all category attributes
     */
    ApiResponse<List<CategoryAttributeDto>> getAllCategoryAttributes();

    /**
     * Retrieves a category attribute by its ID.
     *
     * @param id the ID of the category attribute
     * @return the category attribute with the specified ID, or null if not found
     */
    ApiResponse<CategoryAttributeDto> getCategoryAttributeById(UUID id);



}

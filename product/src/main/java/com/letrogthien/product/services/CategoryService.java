package com.letrogthien.product.services;

import java.util.List;
import java.util.UUID;

import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.dto.CategoryDto;


/**
 * Service interface for managing {@link Category} entities.
 */
public interface CategoryService {
    // Define methods for managing categories, e.g., create, update, delete, find by ID, etc.


    /**
     * Finds a category by its ID.
     *
     * @param id the ID of the category
     * @return the found category, or null if not found
     */
    ApiResponse<CategoryDto> findCategoryById(UUID id);

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    ApiResponse<List<CategoryDto>> findAllCategories();

    /**
     * Finds categories by their parent ID.
     *
     * @param parentId the parent category ID
     * @return a list of categories with the given parent ID
     */
    ApiResponse<List<CategoryDto>> findCategoriesByParentId(UUID parentId);

    /**
     * Finds categories by their name.
     *
     * @param name the name of the category
     * @return a list of categories with the given name
     */
    ApiResponse<List<CategoryDto>> findCategoriesByName(String name);


}

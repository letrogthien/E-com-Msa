package com.letrogthien.product.services;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.request.CategoryAttributeRequest;
import com.letrogthien.product.request.CategoryRequest;
import com.letrogthien.product.responses.ApiResponse;
public interface AdminService {
        /**
     * Creates a new category.
     *
     * @param category the category to create
     * @return the created category
     */
    ApiResponse<CategoryDto> createCategory(CategoryRequest category, MultipartFile icon);

    /**
     * Updates an existing category.
     *
     * @param category the category with updated information
     * @return the updated category
     */
    ApiResponse<CategoryDto> updateCategory(CategoryRequest category, MultipartFile icon);

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     */
    ApiResponse<CategoryDto> deleteCategory(UUID id);

        /**
     * Saves a new or existing category attribute.
     *
     * @param categoryAttribute the category attribute to save
     * @return the saved category attribute
     */
    ApiResponse<CategoryAttributeDto> saveCategoryAttribute(CategoryAttributeRequest categoryAttribute);
    /**
     * Deletes a category attribute by its ID.
     *
     * @param id the ID of the category attribute to delete
     */
    ApiResponse<CategoryAttributeDto> deleteCategoryAttribute(UUID id);

}

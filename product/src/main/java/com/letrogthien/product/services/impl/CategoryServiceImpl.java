package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.entities.Category;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.CategoryMapper;
import com.letrogthien.product.repositories.CategoryRepository;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public ApiResponse<CategoryDto> findCategoryById(UUID id) {
        return categoryRepository.findById(id)
            .map(category -> ApiResponse.<CategoryDto>builder()
                .data(categoryMapper.toDto(category))
                .message("Category found successfully")
                .build())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<List<CategoryDto>> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ApiResponse.<List<CategoryDto>>builder()
            .data(categoryMapper.toDtoList(categories))
            .message("All categories retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<List<CategoryDto>> findCategoriesByParentId(UUID parentId) {
        List<Category> categories = categoryRepository.findByParentId(parentId);
        return ApiResponse.<List<CategoryDto>>builder()
            .data(categoryMapper.toDtoList(categories))
            .message("Categories with parent ID " + parentId + " retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<List<CategoryDto>> findCategoriesByName(String name) {
        List<Category> categories = categoryRepository.findByName(name);
        return ApiResponse.<List<CategoryDto>>builder()
            .data(categoryMapper.toDtoList(categories))
            .message("Categories with name '" + name + "' retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<List<CategoryDto>> findCategoriesByAttribute(String attributeName, String attributeValue) {
        List<Category> categories = categoryRepository.findByAttributeNameAndAttributeValue(attributeName, attributeValue);
        return ApiResponse.<List<CategoryDto>>builder()
            .data(categoryMapper.toDtoList(categories))
            .message("Categories with " + attributeName + " = '" + attributeValue + "' retrieved successfully")
            .build();
    }


    
}

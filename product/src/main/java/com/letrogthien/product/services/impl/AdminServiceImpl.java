package com.letrogthien.product.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.letrogthien.product.common.Status;
import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.entities.Category;
import com.letrogthien.product.entities.CategoryAttribute;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.CategoryAttributeMapper;
import com.letrogthien.product.mapper.CategoryMapper;
import com.letrogthien.product.repositories.CategoryAttributeRepository;
import com.letrogthien.product.repositories.CategoryRepository;
import com.letrogthien.product.request.CategoryAttributeRequest;
import com.letrogthien.product.request.CategoryRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.AdminService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileStorageService fileStorageService;
    private final CategoryAttributeMapper categoryAttributeMapper;
    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public ApiResponse<CategoryDto> createCategory(CategoryRequest category, MultipartFile icon) {
        if (category.getParentId() == null) {
    
            return ApiResponse.<CategoryDto>builder()
                    .data(categoryMapper.toDto(nonParent(category, icon)))
                    .message("Category created successfully")
                    .build();
        }

        return ApiResponse.<CategoryDto>builder()
                .data(categoryMapper.toDto(haveParent(category, icon)))
                .message("Category created successfully")
                .build();
    }

    private Category nonParent(CategoryRequest category, MultipartFile icon) {
        Category c = categoryMapper.toEntity(category);
        c.setParent(null);
        c.setIconUrl(fileStorageService.saveFile(icon, "categories/" + c.getId()));
        return categoryRepository.save(c);
    }
    private Category haveParent(CategoryRequest category, MultipartFile icon) {
        Category parent = categoryRepository.findById(category.getParentId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        Category e = categoryMapper.toEntity(category);
        e.setParent(parent);
        e.setIconUrl(fileStorageService.saveFile(icon, "categories/" + e.getId()));
        return categoryRepository.save(e);
    }

    @Override
    public ApiResponse<CategoryDto> updateCategory(CategoryRequest category, MultipartFile icon) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if (existingCategory.getParent() == null) {
            return ApiResponse.<CategoryDto>builder()
                    .data(categoryMapper.toDto(nonParent(category, icon)))
                    .message("Category updated successfully")
                    .build();
        } 
        return ApiResponse.<CategoryDto>builder()
                .data(categoryMapper.toDto(haveParent(category, icon)))
                .message("Category updated successfully")
                .build();
    }

    @Override
    public ApiResponse<CategoryDto> deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        category.setStatus(Status.DELETED);
        return ApiResponse.<CategoryDto>builder()
                .data(categoryMapper.toDto(category))
                .message("Category deleted successfully")
                .build();
    }

    @Override
    public ApiResponse<CategoryAttributeDto> saveCategoryAttribute(CategoryAttributeRequest categoryAttribute) {
        CategoryAttribute attribute = categoryAttributeRepository.findById(categoryAttribute.getCategoryId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        categoryAttributeMapper.updateEntity(attribute, categoryAttribute);
        categoryAttributeRepository.save(attribute);
        return ApiResponse.<CategoryAttributeDto>builder()
                .data(categoryAttributeMapper.toDto(attribute))
                .message("Category attribute saved successfully")
                .build();
    }

    @Override
    public ApiResponse<CategoryAttributeDto> deleteCategoryAttribute(UUID id) {
        CategoryAttribute attribute = categoryAttributeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        attribute.setStatus(Status.DELETED);
        return ApiResponse.<CategoryAttributeDto>builder()
                .data(categoryAttributeMapper.toDto(attribute))
                .message("Category attribute deleted successfully")
                .build();
    }


}

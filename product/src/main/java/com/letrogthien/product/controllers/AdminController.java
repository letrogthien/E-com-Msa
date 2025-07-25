package com.letrogthien.product.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.letrogthien.product.request.CategoryAttributeRequest;
import com.letrogthien.product.request.CategoryRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.services.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    // Category Endpoints
    @PostMapping("/category")
    public ApiResponse<CategoryDto> createCategory(@ModelAttribute CategoryRequest category, @RequestParam("icon") MultipartFile icon) {
        return adminService.createCategory(category, icon);
    }

    @PutMapping("/category")
    public ApiResponse<CategoryDto> updateCategory(@ModelAttribute CategoryRequest category, @RequestParam("icon") MultipartFile icon) {
        return adminService.updateCategory(category, icon);
    }

    @DeleteMapping("/category/{id}")
    public ApiResponse<CategoryDto> deleteCategory(@PathVariable UUID id) {
        return adminService.deleteCategory(id);
    }

    // Category Attribute Endpoints
    @PostMapping("/category-attribute")
    public ApiResponse<CategoryAttributeDto> saveCategoryAttribute(@RequestBody CategoryAttributeRequest categoryAttribute) {
        return adminService.saveCategoryAttribute(categoryAttribute);
    }

    @DeleteMapping("/category-attribute/{id}")
    public ApiResponse<CategoryAttributeDto> deleteCategoryAttribute(@PathVariable UUID id) {
        return adminService.deleteCategoryAttribute(id);
    }
}

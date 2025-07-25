package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.services.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> findCategoryById(@PathVariable UUID id) {
        return categoryService.findCategoryById(id);
    }

    @GetMapping
    public ApiResponse<List<CategoryDto>> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/parent/{parentId}")
    public ApiResponse<List<CategoryDto>> findCategoriesByParentId(@PathVariable UUID parentId) {
        return categoryService.findCategoriesByParentId(parentId);
    }

    @GetMapping("/name/{name}")
    public ApiResponse<List<CategoryDto>> findCategoriesByName(@PathVariable String name) {
        return categoryService.findCategoriesByName(name);
    }
}

package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.services.CategoryAttributeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category-attribute")
@RequiredArgsConstructor
public class CategoryAttributeController {
    private final CategoryAttributeService categoryAttributeService;

    @GetMapping
    public ApiResponse<List<CategoryAttributeDto>> getAllCategoryAttributes() {
        return categoryAttributeService.getAllCategoryAttributes();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryAttributeDto> getCategoryAttributeById(@PathVariable UUID id) {
        return categoryAttributeService.getCategoryAttributeById(id);
    }
}

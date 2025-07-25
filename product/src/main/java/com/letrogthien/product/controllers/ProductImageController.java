package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.dto.ProductImageDto;
import com.letrogthien.product.request.ProductImageRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product-image")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @GetMapping
    public ApiResponse<List<ProductImageDto>> getAllProductImages() {
        return productImageService.getAllProductImages();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductImageDto> getProductImageById(@PathVariable UUID id) {
        return productImageService.getProductImageById(id);
    }

    @PostMapping
    public ApiResponse<ProductImageDto> saveProductImage(@RequestBody ProductImageRequest productImage) {
        return productImageService.saveProductImage(productImage);
    }

    @PutMapping
    public ApiResponse<ProductImageDto> updateProductImage(@RequestBody ProductImageRequest productImage) {
        return productImageService.updateProductImage(productImage);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductImage(@PathVariable UUID id) {
        return productImageService.deleteProductImage(id);
    }
}

package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.dto.ProductVariantDto;
import com.letrogthien.product.request.ProductVariantRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductVariantService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product-variant")
@RequiredArgsConstructor
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @GetMapping
    public ApiResponse<List<ProductVariantDto>> getAllProductVariants() {
        return productVariantService.getAllProductVariants();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductVariantDto> getProductVariantById(@PathVariable UUID id) {
        return productVariantService.getProductVariantById(id);
    }

    @PostMapping
    public ApiResponse<ProductVariantDto> saveProductVariant(@RequestBody ProductVariantRequest productVariant) {
        return productVariantService.saveProductVariant(productVariant);
    }

    @PutMapping
    public ApiResponse<ProductVariantDto> updateProductVariant(@RequestBody ProductVariantRequest productVariant) {
        return productVariantService.updateProductVariant(productVariant);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductVariant(@PathVariable UUID id) {
        return productVariantService.deleteProductVariant(id);
    }
}

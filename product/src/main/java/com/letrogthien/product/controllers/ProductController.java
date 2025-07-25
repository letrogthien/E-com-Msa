package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.dto.ProductDto;
import com.letrogthien.product.request.ProductRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ApiResponse<ProductDto> saveProduct(@RequestBody ProductRequest productRq) {
        return productService.saveProduct(productRq);
    }

    @PutMapping
    public ApiResponse<ProductDto> updateProduct(@RequestBody ProductRequest productRq) {
        return productService.updateProduct(productRq);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ProductDto> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }
}

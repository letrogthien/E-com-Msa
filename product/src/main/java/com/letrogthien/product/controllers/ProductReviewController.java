package com.letrogthien.product.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.letrogthien.product.dto.ProductReviewDto;
import com.letrogthien.product.request.ProductReviewRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductReviewService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product-review")
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @GetMapping
    public ApiResponse<List<ProductReviewDto>> getAllProductReviews() {
        return productReviewService.getAllProductReviews();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductReviewDto> getProductReviewById(@PathVariable UUID id) {
        return productReviewService.getProductReviewById(id);
    }

    @PostMapping
    public ApiResponse<ProductReviewDto> saveProductReview(@RequestBody ProductReviewRequest productReview) {
        return productReviewService.saveProductReview(productReview);
    }

    @PutMapping
    public ApiResponse<ProductReviewDto> updateProductReview(@RequestBody ProductReviewRequest productReview) {
        return productReviewService.updateProductReview(productReview);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductReview(@PathVariable UUID id) {
        return productReviewService.deleteProductReview(id);
    }
}

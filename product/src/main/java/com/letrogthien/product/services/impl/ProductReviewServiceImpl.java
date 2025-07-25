package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.letrogthien.product.common.Status;
import com.letrogthien.product.dto.ProductReviewDto;
import com.letrogthien.product.entities.ProductReview;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.ProductReviewMapper;
import com.letrogthien.product.repositories.ProductRepository;
import com.letrogthien.product.repositories.ProductReviewRepository;
import com.letrogthien.product.request.ProductReviewRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService{

    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewMapper productReviewMapper;

    
    
    @Override
    public ApiResponse<List<ProductReviewDto>> getAllProductReviews() {

        return ApiResponse.<List<ProductReviewDto>>builder()
            .data(productReviewMapper.toDtoList(productReviewRepository.findAll()))
            .message("All product reviews retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductReviewDto> getProductReviewById(UUID id) {
        return productReviewRepository.findById(id)
            .map(review -> ApiResponse.<ProductReviewDto>builder()
                .data(productReviewMapper.toDto(review))
                .message("Product review found successfully")
                .build())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<ProductReviewDto> saveProductReview(ProductReviewRequest productReview) {
        ProductReview entity = productReviewMapper.toEntity(productReview);
        entity.setProduct(productRepository.findById(productReview.getProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
        entity.setStatus(Status.ACTIVE);
        ProductReview savedReview = productReviewRepository.save(entity);
        return ApiResponse.<ProductReviewDto>builder()
            .data(productReviewMapper.toDto(savedReview))
            .message("Product review saved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductReviewDto> updateProductReview(ProductReviewRequest productReview) {
        ProductReview existingReview = productReviewRepository.findById(productReview.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        
        ProductReview updatedReview = productReviewMapper.toEntity(productReview);
        updatedReview.setId(existingReview.getId());
        updatedReview.setProduct(existingReview.getProduct());
        updatedReview.setStatus(existingReview.getStatus());
        
        ProductReview savedReview = productReviewRepository.save(updatedReview);
        return ApiResponse.<ProductReviewDto>builder()
            .data(productReviewMapper.toDto(savedReview))
            .message("Product review updated successfully")
            .build();
    }

    @Override
    public ApiResponse<Void> deleteProductReview(UUID id) {
        ProductReview review = productReviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        
        review.setStatus(Status.DELETED);
        productReviewRepository.save(review);
        
        return ApiResponse.<Void>builder()
            .message("Product review deleted successfully")
            .build();
    }

}
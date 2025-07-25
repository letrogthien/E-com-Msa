package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;


import com.letrogthien.product.common.Status;
import com.letrogthien.product.dto.ProductImageDto;
import com.letrogthien.product.entities.ProductImage;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.ProductImageMapper;
import com.letrogthien.product.repositories.ProductImageRepository;
import com.letrogthien.product.request.ProductImageRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    @Override
    public ApiResponse<List<ProductImageDto>> getAllProductImages() {
        List<ProductImage> images = productImageRepository.findAll();
        return ApiResponse.<List<ProductImageDto>>builder()
            .data(productImageMapper.toDtoList(images))
            .message("All product images retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductImageDto> getProductImageById(UUID id) {
        return productImageRepository.findById(id)
            .map(image -> ApiResponse.<ProductImageDto>builder()
                .data(productImageMapper.toDto(image))
                .message("Product image found successfully")
                .build())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<ProductImageDto> saveProductImage(ProductImageRequest productImageRq) {
        ProductImage image = productImageMapper.toEntity(productImageRq);
        image = productImageRepository.save(image);
        return ApiResponse.<ProductImageDto>builder()
            .data(productImageMapper.toDto(image))
            .message("Product image saved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductImageDto> updateProductImage(ProductImageRequest productImageRq) {
        ProductImage image = productImageRepository.findById(productImageRq.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        productImageMapper.updateEntity(image, productImageRq);
        image = productImageRepository.save(image);
        return ApiResponse.<ProductImageDto>builder()
            .data(productImageMapper.toDto(image))
            .message("Product image updated successfully")
            .build();
    }

    @Override
    public ApiResponse<Void> deleteProductImage(UUID id) {
        ProductImage image = productImageRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        image.setStatus(Status.DELETED);
        productImageRepository.save(image);
        return ApiResponse.<Void>builder()
            .message("Product image deleted successfully")
            .build();
    }

    @Override
    public ApiResponse<List<ProductImageDto>> findProductImagesByProductId(UUID productId) {
        List<ProductImage> images = productImageRepository.findByProductId(productId);
        return ApiResponse.<List<ProductImageDto>>builder()
            .data(productImageMapper.toDtoList(images))
            .message("Product images found for product ID " + productId)
            .build();
    }
}
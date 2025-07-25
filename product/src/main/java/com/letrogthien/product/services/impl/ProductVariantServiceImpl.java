package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.letrogthien.product.dto.ProductVariantDto;
import com.letrogthien.product.entities.ProductVariant;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.ProductVariantMapper;
import com.letrogthien.product.repositories.ProductRepository;
import com.letrogthien.product.repositories.ProductVariantRepository;
import com.letrogthien.product.request.ProductVariantRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductVariantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    public ApiResponse<List<ProductVariantDto>> getAllProductVariants() {
        List<ProductVariantDto> variantDtos = productVariantMapper.toDtoList(
                productVariantRepository.findAll());
        return ApiResponse.<List<ProductVariantDto>>builder()
                .data(variantDtos)
                .message("Retrieved all product variants successfully")
                .build();
    }

    @Override
    public ApiResponse<ProductVariantDto> getProductVariantById(UUID id) {
        ProductVariantDto variantDto = productVariantMapper.toDto(
                productVariantRepository.findById(id).orElse(null));
        return ApiResponse.<ProductVariantDto>builder()
                .data(variantDto)
                .message("Retrieved product variant successfully")
                .build();
    }

    @Override
    public ApiResponse<ProductVariantDto> saveProductVariant(ProductVariantRequest productVariant) {
        ProductVariant entity = productVariantMapper.toEntity(productVariant);
        entity.setProduct(productRepository.findById(productVariant.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
        ProductVariantDto variantDto = productVariantMapper.toDto(
                productVariantRepository.save(entity));
        return ApiResponse.<ProductVariantDto>builder()
                .data(variantDto)
                .message("Saved product variant successfully")
                .build();
    }

    @Override
    public ApiResponse<ProductVariantDto> updateProductVariant(ProductVariantRequest productVariant) {
        ProductVariantDto variantDto = productVariantMapper.toDto(
                productVariantRepository.save(productVariantMapper.toEntity(productVariant)));
        return ApiResponse.<ProductVariantDto>builder()
                .data(variantDto)
                .message("Updated product variant successfully")
                .build();
    }

    @Override
    public ApiResponse<Void> deleteProductVariant(UUID id) {
        productVariantRepository.deleteById(id);
        return ApiResponse.<Void>builder()
                .message("Deleted product variant successfully")
                .build();
    }
}
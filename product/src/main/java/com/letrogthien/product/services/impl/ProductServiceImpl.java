package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.letrogthien.product.common.Status;
import com.letrogthien.product.dto.ProductDto;
import com.letrogthien.product.entities.Product;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.ProductMapper;
import com.letrogthien.product.repositories.CategoryRepository;
import com.letrogthien.product.repositories.ProductRepository;
import com.letrogthien.product.request.ProductRequest;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.ProductService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ApiResponse.<List<ProductDto>>builder()
            .data(productMapper.toDtoList(products))
            .message("All products retrieved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductDto> getProductById(UUID id) {
        return productRepository.findById(id)
            .map(product -> ApiResponse.<ProductDto>builder()
                .data(productMapper.toDto(product))
                .message("Product found successfully")
                .build())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<ProductDto> deleteProduct(UUID id) {
        return productRepository.findById(id)
            .map(product -> {
                product.setStatus(Status.DELETED);
                return ApiResponse.<ProductDto>builder()
                    .data(productMapper.toDto(productRepository.save(product)))
                    .message("Product deleted successfully")
                    .build();
            })
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<List<ProductDto>> findProductsByCategoryId(UUID categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ApiResponse.<List<ProductDto>>builder()
            .data(productMapper.toDtoList(products))
            .message("Products found for category ID " + categoryId)
            .build();
            
    }

    @Override
    public ApiResponse<ProductDto> saveProduct(ProductRequest productRq) {
        Product product = productMapper.toEntity(productRq);
        product = productRepository.save(product);
        return ApiResponse.<ProductDto>builder()
            .data(productMapper.toDto(product))
            .message("Product saved successfully")
            .build();
    }

    @Override
    public ApiResponse<ProductDto> updateProduct(ProductRequest productRq) {
            Product product = productRepository.findById(productRq.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
            productMapper.updateEntity(product, productRq);
            product.setCategory(categoryRepository.findById(productRq.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
            product = productRepository.save(product);
            return ApiResponse.<ProductDto>builder()
                .data(productMapper.toDto(product))
                .message("Product updated successfully")
                .build();
    }
    
}

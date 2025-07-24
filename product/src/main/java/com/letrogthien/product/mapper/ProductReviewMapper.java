package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.ProductReview;
import com.letrogthien.product.request.ProductReviewRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.product.dto.ProductReviewDto;


@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    ProductReviewMapper INSTANCE = Mappers.getMapper(ProductReviewMapper.class);

    
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "userId", target = "reviewerId")
    @Mapping(source = "ratingScore", target = "rating")
    @Mapping(source = "reviewText", target = "comment")
    ProductReviewDto toDto(ProductReview entity);

    @Mapping(source = "reviewerId", target = "userId")
    @Mapping(source = "rating", target = "ratingScore")
    @Mapping(source = "comment", target = "reviewText")
    @Mapping(source = "productId", target = "product.id")
    // @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    ProductReview toEntity(ProductReviewRequest request);

}

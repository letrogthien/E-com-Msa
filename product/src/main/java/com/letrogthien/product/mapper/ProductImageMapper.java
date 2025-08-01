package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.ProductImage;
import com.letrogthien.product.request.ProductImageRequest;


import com.letrogthien.product.dto.ProductImageDto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")

public interface ProductImageMapper {
    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

    @Mapping(source = "product.id", target = "productId")
    ProductImageDto toDto(ProductImage entity);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    ProductImage toEntity(ProductImageRequest request);

    List<ProductImageDto> toDtoList(List<ProductImage> images);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget ProductImage image, ProductImageRequest productImageRq);
}

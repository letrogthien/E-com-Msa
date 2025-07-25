package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.ProductVariant;
import com.letrogthien.product.request.ProductVariantRequest;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.product.dto.ProductVariantDto;


@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(source = "product.id", target = "productId")
    ProductVariantDto toDto(ProductVariant entity);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "priceAdjustment", source = "priceAdjustment")
    @Mapping(target = "stockQuantity", source = "stockQuantity")
    @Mapping(target = "thumbnailUrl", source = "thumbnailUrl")
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "variantAttributesJson", target = "variantAttributesJson")
    ProductVariant toEntity(ProductVariantRequest request);

    List<ProductVariantDto> toDtoList(List<ProductVariant> all);


}

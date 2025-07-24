package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.ProductVariant;
import com.letrogthien.product.request.ProductVariantRequest;
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
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "priceAdjustment", ignore = true)
    @Mapping(target = "stockQuantity", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    @Mapping(source = "variantAttributesJson", target = "variantAttributesJson")
    ProductVariant toEntity(ProductVariantRequest request);


}

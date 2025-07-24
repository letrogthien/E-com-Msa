package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.Product;
import com.letrogthien.product.request.ProductRequest;
import com.letrogthien.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "sellerId", target = "sellerId")
    ProductDto toDto(Product entity);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);
}

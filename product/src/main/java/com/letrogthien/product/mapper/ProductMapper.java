package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.Product;
import com.letrogthien.product.request.ProductRequest;
import com.letrogthien.product.dto.ProductDto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(source = "status", target = "status")
    ProductDto toDto(Product entity);

    @Mapping(target = "category", ignore = true)
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    Product toEntity(ProductRequest request);

    List<ProductDto> toDtoList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget Product entity, ProductRequest request);
}

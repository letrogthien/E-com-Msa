package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.CategoryAttribute;
import com.letrogthien.product.request.CategoryAttributeRequest;
import com.letrogthien.product.dto.CategoryAttributeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryAttributeMapper {
    CategoryAttributeMapper INSTANCE = Mappers.getMapper(CategoryAttributeMapper.class);

    @Mapping(source = "categoryAttribute.id", target = "id")
    @Mapping(source = "categoryAttribute.category.id", target = "categoryId")
    @Mapping(source = "isRequired", target = "required")
    CategoryAttributeDto toDto(CategoryAttribute categoryAttribute);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "isRequired", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CategoryAttribute toEntity(CategoryAttributeRequest request);
}

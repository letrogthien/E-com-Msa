package com.letrogthien.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDto toDto(Category category);

    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(com.letrogthien.product.request.CategoryRequest request);
}

package com.letrogthien.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.product.dto.CategoryDto;
import com.letrogthien.product.entities.Category;
import com.letrogthien.product.request.CategoryRequest;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDto toDto(Category category);

    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "iconUrl", ignore = true)
    Category toEntity(CategoryRequest request);

    List<CategoryDto> toDtoList(List<Category> categories);
}

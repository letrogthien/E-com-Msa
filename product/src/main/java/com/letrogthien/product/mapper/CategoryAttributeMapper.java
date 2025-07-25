package com.letrogthien.product.mapper;

import com.letrogthien.product.entities.CategoryAttribute;
import com.letrogthien.product.request.CategoryAttributeRequest;
import com.letrogthien.product.dto.CategoryAttributeDto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryAttributeMapper {
    CategoryAttributeMapper INSTANCE = Mappers.getMapper(CategoryAttributeMapper.class);

    @Mapping(source = "categoryAttribute.id", target = "id")
    @Mapping(source = "categoryAttribute.category.id", target = "categoryId")
    @Mapping(source = "isRequired", target = "required")
    CategoryAttributeDto toDto(CategoryAttribute categoryAttribute);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isRequired", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    CategoryAttribute toEntity(CategoryAttributeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isRequired", source = "required")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget CategoryAttribute entity, CategoryAttributeRequest dto);

    List<CategoryAttributeDto> toDtoList(List<CategoryAttribute> entities);
}

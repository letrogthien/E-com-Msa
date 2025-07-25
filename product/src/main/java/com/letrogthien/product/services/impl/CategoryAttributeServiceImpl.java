package com.letrogthien.product.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.letrogthien.product.dto.CategoryAttributeDto;
import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import com.letrogthien.product.mapper.CategoryAttributeMapper;
import com.letrogthien.product.repositories.CategoryAttributeRepository;
import com.letrogthien.product.responses.ApiResponse;
import com.letrogthien.product.services.CategoryAttributeService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoryAttributeServiceImpl implements CategoryAttributeService {
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final CategoryAttributeMapper categoryAttributeMapper;

    @Override
    public ApiResponse<List<CategoryAttributeDto>> getAllCategoryAttributes() {
    
        List<CategoryAttributeDto> attributeDtos = categoryAttributeMapper.toDtoList(
            categoryAttributeRepository.findAll()
        );
        return ApiResponse.<List<CategoryAttributeDto>>builder()
                .data(attributeDtos)
                .message("Retrieved all category attributes successfully")
                .build();
    }
    

    @Override
    public ApiResponse<CategoryAttributeDto> getCategoryAttributeById(UUID id) {
        CategoryAttributeDto attributeDto = categoryAttributeMapper.toDto(
            categoryAttributeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND))
        );
        return ApiResponse.<CategoryAttributeDto>builder()
                .data(attributeDto)
                .message("Retrieved category attribute successfully")
                .build();
    }
    
}

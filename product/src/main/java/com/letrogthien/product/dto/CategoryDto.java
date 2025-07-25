package com.letrogthien.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.letrogthien.product.common.Status;

import lombok.Data;


@Data
public class CategoryDto {

    private UUID id;


    private String name;

    private String slug;

    private UUID parentId;

    private Status status;

    private String description;

    private String iconUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

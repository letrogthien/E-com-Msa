package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

import com.letrogthien.product.common.Status;

@Data
public class CategoryAttributeDto {
    private UUID id;
    private UUID categoryId;
    private String attributeName;
    private String attributeType;
    private Status status;
    private boolean isRequired;
    private String optionsJson;
    private String validationRegex;
    private int displayOrder;
}

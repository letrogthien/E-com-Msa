package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoryAttributeDto {
    private UUID id;
    private UUID categoryId;
    private String attributeName;
    private String attributeType;
    private boolean isRequired;
    private String optionsJson;
    private String validationRegex;
    private int displayOrder;
}

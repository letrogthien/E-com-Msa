package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoryAttributeRequest {
    private UUID categoryId;
    private String attributeName;
    private String attributeType;
    private boolean isRequired;
    private String optionsJson;
    private String validationRegex;
    private int displayOrder;
}

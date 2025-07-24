package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductVariantDto {
    private UUID id;
    private UUID productId;
    private String sku;
    private String variantAttributesJson;
}

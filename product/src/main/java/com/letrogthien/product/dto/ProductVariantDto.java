package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

import com.letrogthien.product.common.Status;

@Data
public class ProductVariantDto {
    private UUID id;
    private UUID productId;
    private Status status;
    private String sku;
    private String variantAttributesJson;
}

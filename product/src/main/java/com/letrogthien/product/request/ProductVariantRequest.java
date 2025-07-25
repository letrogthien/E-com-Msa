package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductVariantRequest {
    private UUID id;
    private UUID productId;
    private String sku;
    private Double priceAdjustment;
    private int stockQuantity;
    private String thumbnailUrl;
    private String variantAttributesJson;
}

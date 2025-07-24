package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductVariantRequest {
    private UUID productId;
    private String variantAttributesJson;
}

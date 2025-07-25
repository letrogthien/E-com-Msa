package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductImageRequest {
    private UUID id;
    private UUID productId;
    private String imageUrl;
    private int displayOrder;
}

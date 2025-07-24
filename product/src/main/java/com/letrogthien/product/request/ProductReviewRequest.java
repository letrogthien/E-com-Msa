package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductReviewRequest {
    private UUID productId;
    private UUID reviewerId;
    private int rating;
    private String comment;
}

package com.letrogthien.product.request;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoryRequest {
    private UUID id;
    private String name;
    private String slug;
    private UUID parentId;
    private String description;
}

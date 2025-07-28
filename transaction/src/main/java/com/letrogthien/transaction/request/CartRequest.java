package com.letrogthien.transaction.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CartRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;
}

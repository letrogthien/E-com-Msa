package com.letrogthien.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class BillingAddressDto {
    private UUID id;
    private String address;
    private String city;
    private String postalCode;
    private String state;
    private String province;
    private String countryRegion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

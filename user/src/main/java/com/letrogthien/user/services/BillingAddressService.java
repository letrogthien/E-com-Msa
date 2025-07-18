package com.letrogthien.user.services;


import com.letrogthien.user.dtos.BillingAddressDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface BillingAddressService {
    ApiResponse<BillingAddressDto> saveBillingAddress(BillingAddressDto request);

    ApiResponse<BillingAddressDto> updateBillingAddress(UUID id, BillingAddressDto request);

    ApiResponse<BillingAddressDto> getBillingAddressById(UUID id);

    ApiResponse<List<BillingAddressDto>> getAllBillingAddresses();

    ApiResponse<Void> deleteBillingAddress(UUID id);

    ApiResponse<List<BillingAddressDto>> findByCountryRegion(String countryRegion);

    ApiResponse<BillingAddressDto> findByPostalCode(String postalCode);

    ApiResponse<Boolean> existsById(UUID id);
}
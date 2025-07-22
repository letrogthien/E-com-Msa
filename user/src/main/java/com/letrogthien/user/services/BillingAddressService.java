package com.letrogthien.user.services;


import com.letrogthien.user.dtos.BillingAddressDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface BillingAddressService {

    /**
     * Saves a new billing address.
     *
     * @param request The billing address data to save.
     * @return An ApiResponse containing the saved BillingAddressDto.
     */
    ApiResponse<BillingAddressDto> saveBillingAddress(BillingAddressDto request);

    /**
     * Updates an existing billing address by its ID.
     *
     * @param id      The UUID of the billing address to update.
     * @param request The updated billing address data.
     * @return An ApiResponse containing the updated BillingAddressDto.
     */
    ApiResponse<BillingAddressDto> updateBillingAddress(UUID id, BillingAddressDto request);

    /**
     * Retrieves a billing address by its ID.
     *
     * @param id The UUID of the billing address.
     * @return An ApiResponse containing the requested BillingAddressDto.
     */
    ApiResponse<BillingAddressDto> getBillingAddressById(UUID id);

    /**
     * Retrieves all billing addresses.
     *
     * @return An ApiResponse containing a list of all BillingAddressDto.
     */
    ApiResponse<List<BillingAddressDto>> getAllBillingAddresses();

    /**
     * Deletes a billing address by its ID.
     * This operation should be used with caution; consider soft delete instead.
     *
     * @param id The UUID of the billing address to delete.
     * @return An ApiResponse indicating success or failure of the deletion.
     */
    ApiResponse<Void> deleteBillingAddress(UUID id);

    /**
     * Finds billing addresses by country or region.
     *
     * @param countryRegion The country or region to filter addresses.
     * @return An ApiResponse containing a list of BillingAddressDto matching the countryRegion.
     */
    ApiResponse<List<BillingAddressDto>> findByCountryRegion(String countryRegion);

    /**
     * Finds a billing address by its postal code.
     *
     * @param postalCode The postal code to filter the address.
     * @return An ApiResponse containing the BillingAddressDto matching the postal code.
     */
    ApiResponse<BillingAddressDto> findByPostalCode(String postalCode);

    /**
     * Checks whether a billing address exists by its ID.
     *
     * @param id The UUID of the billing address.
     * @return An ApiResponse containing a boolean indicating the existence of the address.
     */
    ApiResponse<Boolean> existsById(UUID id);
}

package com.letrogthien.user.controllers;

import com.letrogthien.user.dtos.BillingAddressDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/billing-addresses")
@RequiredArgsConstructor
public class BillingAddressController {
    private final BillingAddressService billingAddressService;

    @PostMapping
    public ApiResponse<BillingAddressDto> createBillingAddress(@RequestBody BillingAddressDto request) {
        return billingAddressService.saveBillingAddress(request);
    }

    @PutMapping("/{id}")
    public ApiResponse<BillingAddressDto> updateBillingAddress(@PathVariable UUID id, @RequestBody BillingAddressDto request) {
        return billingAddressService.updateBillingAddress(id, request);
    }

    @GetMapping("/{id}")
    public ApiResponse<BillingAddressDto> getBillingAddressById(@PathVariable UUID id) {
        return billingAddressService.getBillingAddressById(id);
    }

    @GetMapping
    public ApiResponse<List<BillingAddressDto>> getAllBillingAddresses() {
        return billingAddressService.getAllBillingAddresses();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBillingAddress(@PathVariable UUID id) {
        return billingAddressService.deleteBillingAddress(id);
    }

    @GetMapping("/country/{countryRegion}")
    public ApiResponse<List<BillingAddressDto>> findByCountryRegion(@PathVariable String countryRegion) {
        return billingAddressService.findByCountryRegion(countryRegion);
    }

    @GetMapping("/postal-code/{postalCode}")
    public ApiResponse<BillingAddressDto> findByPostalCode(@PathVariable String postalCode) {
        return billingAddressService.findByPostalCode(postalCode);
    }

    @GetMapping("/exists/{id}")
    public ApiResponse<Boolean> existsById(@PathVariable UUID id) {
        return billingAddressService.existsById(id);
    }
}

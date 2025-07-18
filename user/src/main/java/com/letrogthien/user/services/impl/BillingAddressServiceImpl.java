package com.letrogthien.user.services.impl;

import com.letrogthien.user.dtos.BillingAddressDto;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.BillingAddressMapper;
import com.letrogthien.user.repositories.BillingAddressRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BillingAddressServiceImpl implements BillingAddressService {
    private final BillingAddressRepository billingAddressRepository;
    private final BillingAddressMapper billingAddressMapper;
    @Override
    public ApiResponse<BillingAddressDto> saveBillingAddress(BillingAddressDto request) {
        return ApiResponse.<BillingAddressDto>builder()
                .message("Billing address saved successfully")
                .data(billingAddressMapper.toDto(
                        billingAddressRepository.save(
                                billingAddressMapper.toEntity(request)
                        )
                ))
                .build();
    }

    @Override
    public ApiResponse<BillingAddressDto> updateBillingAddress(UUID id, BillingAddressDto request) {
        return billingAddressRepository.findById(id)
                .map(existingAddress -> {
                    billingAddressMapper.updateBillingAddressFromDto(request, existingAddress);
                    BillingAddressDto updatedAddress = billingAddressMapper.toDto(billingAddressRepository
                            .save(existingAddress));
                    return ApiResponse.<BillingAddressDto>builder()
                            .message("Billing address updated successfully")
                            .data(updatedAddress)
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<BillingAddressDto> getBillingAddressById(UUID id) {
        return billingAddressRepository.findById(id)
                .map(billingAddressMapper::toDto)
                .map(address -> ApiResponse.<BillingAddressDto>builder()
                        .message("Billing address retrieved successfully")
                        .data(address)
                        .build())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<List<BillingAddressDto>> getAllBillingAddresses() {
        return ApiResponse.<List<BillingAddressDto>>builder()
                .message("All billing addresses retrieved successfully")
                .data(billingAddressMapper.toDtoList(billingAddressRepository.findAll()))
                .build();
    }

    @Override
    public ApiResponse<Void> deleteBillingAddress(UUID id) {
        return billingAddressRepository.findById(id)
                .map(existingAddress -> {
                    billingAddressRepository.delete(existingAddress);
                    return ApiResponse.<Void>builder()
                            .message("Billing address deleted successfully")
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<List<BillingAddressDto>> findByCountryRegion(String countryRegion) {
        List<BillingAddressDto> addressDtos = billingAddressMapper.toDtoList(
                billingAddressRepository.findAllByCountryRegion(countryRegion)
        );
        return ApiResponse.<List<BillingAddressDto>>builder()
                .message("Billing addresses retrieved successfully")
                .data(addressDtos)
                .build();
    }

    @Override
    public ApiResponse<BillingAddressDto> findByPostalCode(String postalCode) {
        return billingAddressRepository.findByPostalCode(postalCode)
                .map(billingAddressMapper::toDto)
                .map(address -> ApiResponse.<BillingAddressDto>builder()
                        .message("Billing address retrieved successfully")
                        .data(address)
                        .build())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<Boolean> existsById(UUID id) {
        return ApiResponse.<Boolean>builder()
                .message("Billing address existence checked successfully")
                .data(billingAddressRepository.existsById(id))
                .build();
    }
}

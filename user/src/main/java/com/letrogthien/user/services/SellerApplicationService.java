package com.letrogthien.user.services;


import com.letrogthien.user.dtos.SellerApplicationDto;
import com.letrogthien.user.request.SellerApplicationRequest;
import com.letrogthien.user.responses.ApiResponse;
import java.util.UUID;

public interface SellerApplicationService {

    /**
     * Submits a seller application for the given user.
     *
     * @param userId the ID of the user submitting the application
     * @param request the details of the seller application
     * @return an ApiResponse containing the submitted SellerApplicationDto
     */
    ApiResponse<SellerApplicationDto> submitApplication(UUID userId, SellerApplicationRequest request);
}
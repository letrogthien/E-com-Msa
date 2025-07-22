package com.letrogthien.auth.services;

import com.letrogthien.auth.anotation.CusAuditable;
import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.responses.ApiResponse;

import java.util.UUID;

public interface AdminService {

    /**
     * Approves a user's registration or application.
     *
     * @param userId the ID of the user to approve
     * @return ApiResponse containing a success or failure message
     */
    ApiResponse<String> approveUser(UUID userId);

    /**
     * Rejects a user's registration or application.
     *
     * @param userId the ID of the user to reject
     * @return ApiResponse containing a success or failure message
     */
    ApiResponse<String> rejectUser(UUID userId);

    /**
     * Suspends a user's account.
     *
     * @param userId the ID of the user to suspend
     * @return ApiResponse containing a success or failure message
     */
    ApiResponse<String> suspendUser(UUID userId);

    /**
     * Deletes a user from the system.
     *
     * @param userId the ID of the user to delete
     * @return ApiResponse containing a success or failure message
     */
    ApiResponse<String> deleteUser(UUID userId);

    /**
     * Sets a specific role for a user.
     *
     * @param userId   the ID of the user
     * @param roleName the role to assign to the user
     * @return ApiResponse containing a success or failure message
     */
    ApiResponse<String> setRoleForUser(UUID userId, RoleName roleName);
}
package com.letrogthien.auth.controllers;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/user/{userId}/approve")
    public ApiResponse<String> approveUser(@PathVariable UUID userId) {
        return adminService.approveUser(userId);
    }

    @PutMapping("/user/{userId}/reject")
    public ApiResponse<String> rejectUser(@PathVariable UUID userId) {
        return adminService.rejectUser(userId);
    }

    @PutMapping("/user/{userId}/suspend")
    public ApiResponse<String> suspendUser(@PathVariable UUID userId) {
        return adminService.suspendUser(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable UUID userId) {
        return adminService.deleteUser(userId);
    }

    @PutMapping("/user/{userId}/role")
    public ApiResponse<String> setRoleForUser(@PathVariable UUID userId,
                                              @RequestParam RoleName roleName) {
        return adminService.setRoleForUser(userId, roleName);
    }
}

package com.letrogthien.auth.controllers;


import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import com.letrogthien.auth.anotation.JwtClaims;
import com.letrogthien.auth.requests.AccessTokenRequest;
import com.letrogthien.auth.requests.ChangePwdRequest;
import com.letrogthien.auth.requests.Disable2FaRequest;
import com.letrogthien.auth.requests.ForgotPwdRequest;
import com.letrogthien.auth.requests.LoginRequest;
import com.letrogthien.auth.requests.LogoutRequest;
import com.letrogthien.auth.requests.RegisterRequest;
import com.letrogthien.auth.requests.ResetPwdRequest;
import com.letrogthien.auth.requests.TrustDeviceRequest;
import com.letrogthien.auth.requests.Verify2FaRequest;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest, response);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest logoutRequest) {
        return authService.logout(logoutRequest);
    }

    @PostMapping("/logout-all")
    public ApiResponse<String> logoutAll(@JwtClaims("id") UUID userId) {
        return authService.logoutAll(userId);
    }

    @PostMapping("/token/access")
    public ApiResponse<LoginResponse> accessToken(@RequestBody AccessTokenRequest request) {
        return authService.accessToken(request);
    }

    @PatchMapping("/password/change")
    public ApiResponse<String> changePassword(@RequestBody @Valid ChangePwdRequest request,
                                              @JwtClaims("id") UUID userId) {
        return authService.changePassword(request, userId);
    }

    @PostMapping("/password/forgot")
    public ApiResponse<String> forgotPassword(@RequestBody @Valid ForgotPwdRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/password/reset")
    public ApiResponse<String> resetPassword(@RequestBody @Valid ResetPwdRequest request,
                                             @RequestParam String token) {
        return authService.resetPassword(request, token);
    }

    @PutMapping("/2fa/enable")
    public ApiResponse<String> enableTwoFAuth(@JwtClaims("id") UUID userId) {
        return authService.enableTwoFAuth(userId);
    }

    @PutMapping("/2fa/disable")
    public ApiResponse<String> disableTwoFAuth(@RequestBody Disable2FaRequest request,
                                               @JwtClaims("id") UUID userId) {
        return authService.disableTwoFAuth(request, userId);
    }

    @PostMapping("/2fa/verify")
    public ApiResponse<LoginResponse> verifyTwoFAuth(@RequestBody Verify2FaRequest request,
                                                     HttpServletResponse response) {
        return authService.verifyTwoFAuth(request, response);
    }

    @PutMapping("/device/trust")
    public ApiResponse<String> trustDevice(@RequestBody TrustDeviceRequest request,
                                           @JwtClaims("id") UUID userId) {
        return authService.trustDevice(request.getDeviceName(), request.getDeviceType(), userId);
    }

    @PostMapping("/activate")
    public ApiResponse<String> activateAccount(@RequestParam String token) {
        return authService.activateAccount(token);
    }

    @PutMapping("/role/assign")
    public ApiResponse<String> assignRoleToUser(@JwtClaims("id") UUID userId,
                                                @RequestParam RoleName roleName) {
        return authService.assignRoleToUser(userId, roleName);
    }

    @GetMapping("/test")
    public ApiResponse<String> test() {
        return ApiResponse.<String>builder()
                .message("Test successful")
                .build();
    }

    @GetMapping("/test-authenticated")
    public ApiResponse<String> testAuthenticated(@JwtClaims("id") UUID userId) {
        return ApiResponse.<String>builder()
                .message("Authenticated user ID: " + userId)
                .build();
    }
}

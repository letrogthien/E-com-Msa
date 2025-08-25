package com.letrogthien.wallet.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letrogthien.wallet.annotaion.JwtClaims;
import com.letrogthien.wallet.dtos.VnpayReturnDto;
import com.letrogthien.wallet.responses.ApiResponse;
import com.letrogthien.wallet.services.PayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PayService payService;

    @PostMapping("/deposit")
    public ApiResponse<String> getMethodName(@RequestParam String detail, @RequestParam int total,
            HttpServletRequest httpServletRequest,
            @JwtClaims("id") UUID userId) throws InvalidKeyException, NoSuchAlgorithmException {
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress != null) {
            return payService.payUrlDeposit(ipAddress, total, detail, userId);
        }
        ipAddress = httpServletRequest.getRemoteAddr();
        return payService.payUrlDeposit(ipAddress, total, detail, userId);
    }

    @GetMapping("/vnpay_return")
    public ApiResponse<String> handleVnpayReturnDeposit(VnpayReturnDto vnpayReturnDto)  {
        payService.handleReturnVnpDeposit(vnpayReturnDto);

        return ApiResponse.<String>builder()
                .data("ok")
                .message("ok")
                .build();

    }

}

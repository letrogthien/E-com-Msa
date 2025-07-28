package com.letrogthien.transaction.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letrogthien.transaction.annotaion.JwtClaims;
import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.CartItemDto;
import com.letrogthien.transaction.request.CartItemRequest;
import com.letrogthien.transaction.request.CartRequest;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping("/create")
    public ApiResponse<CartDto> createCart(@RequestBody CartRequest request, @JwtClaims("id") UUID userId) {
        return cartService.createCart(request, userId);
    }

    @GetMapping("/get/user/{userId}")
    public ApiResponse<CartDto> getCart(@PathVariable UUID userId) {
        return cartService.getCartByUserId(userId);
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CartDto> updateCart(@PathVariable UUID id, @RequestBody CartRequest request,
            @JwtClaims("id") UUID userId) {
        return cartService.updateCart(id, request, userId);
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> deleteCart(@PathVariable UUID id, @JwtClaims("id") UUID userId) {
        return cartService.deleteCart(id, userId);
    }

    @PostMapping("/add-item")
    public ApiResponse<CartItemDto> addItemToCart(@RequestBody CartItemRequest request, @JwtClaims("id") UUID userId) {
        return cartService.addItemToCart(request, userId);
    }

    @PutMapping("/update-item/{cartItemId}")
    public ApiResponse<CartItemDto> updateCartItemQuantity(@PathVariable UUID cartItemId,
            @RequestParam Integer quantity,
            @JwtClaims("id") UUID userId) {
        return cartService.updateCartItemQuantity(cartItemId, quantity, userId);
    }

    @PostMapping("/remove-item/{cartItemId}")
    public ApiResponse<Void> removeItemFromCart(@PathVariable UUID cartItemId, @JwtClaims("id") UUID userId) {
        return cartService.removeItemFromCart(cartItemId, userId);
    }

    @GetMapping("/items/{cartId}")
    public ApiResponse<List<CartItemDto>> getCartItems(@PathVariable UUID cartId, @JwtClaims("id") UUID userId) {
        return cartService.getCartItems(cartId, userId);
    }

}

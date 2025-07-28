package com.letrogthien.transaction.service;

import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.CartItemDto;
import com.letrogthien.transaction.request.CartItemRequest;
import com.letrogthien.transaction.request.CartRequest;
import com.letrogthien.transaction.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface CartService {
    /**
     * Create a new cart for a user.
     * @param request
     * @return ApiResponse containing the created CartDto
     */
    ApiResponse<CartDto> createCart(CartRequest request, UUID userId);
    /**
     * Get a cart by user ID.
     * @param userId
     * @return ApiResponse containing the CartDto
     */
    ApiResponse<CartDto> getCartByUserId(UUID userId);
    /**
     * Update an existing cart.
     * @param cartId
     * @param request
     * @return ApiResponse containing the updated CartDto
     */
    ApiResponse<CartDto> updateCart(UUID cartId, CartRequest request, UUID userId);


    /**
     * Delete a cart by ID.
     * @param cartId
     * @return ApiResponse indicating success or failure
     */
    ApiResponse<Void> deleteCart(UUID cartId , UUID userId);
    

    /**
     * Add an item to the cart.
     * @param request
     * @return ApiResponse containing the added CartItemDto
     */
    ApiResponse<CartItemDto> addItemToCart(CartItemRequest request , UUID userId);

    /**
     * Update the quantity of an item in the cart.
     * @param cartItemId
     * @param quantity
     * @return ApiResponse containing the updated CartItemDto
     */
    ApiResponse<CartItemDto> updateCartItemQuantity(UUID cartItemId, Integer quantity, UUID userId);

    /**
     * Remove an item from the cart.
     * @param cartItemId
     * @return ApiResponse indicating success or failure
     */
    ApiResponse<Void> removeItemFromCart(UUID cartItemId, UUID userId);

    /**
     * Get all items in a cart.
     * @param cartId
     * @return ApiResponse containing a list of CartItemDto
     */
    ApiResponse<List<CartItemDto>> getCartItems(UUID cartId, UUID userId);
}

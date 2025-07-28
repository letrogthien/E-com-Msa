package com.letrogthien.transaction.service.impl;

import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.CartItemDto;
import com.letrogthien.transaction.entity.Cart;
import com.letrogthien.transaction.entity.CartItem;
import com.letrogthien.transaction.exceptions.CustomException;
import com.letrogthien.transaction.exceptions.ErrorCode;
import com.letrogthien.transaction.mapper.CartMapper;
import com.letrogthien.transaction.repositories.CartItemRepository;
import com.letrogthien.transaction.repositories.CartRepository;
import com.letrogthien.transaction.request.CartItemRequest;
import com.letrogthien.transaction.request.CartRequest;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.CartService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final Validator validator;

    @Override
    public ApiResponse<CartDto> createCart(CartRequest request, UUID userId) {
        Cart cart = cartMapper.toCartEntity(request);
        cart.setUserId(userId);
        Cart savedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toCartDto(savedCart);

        return ApiResponse.<CartDto>builder()
                .status(HttpStatus.CREATED)
                .message("Cart created successfully")
                .data(cartDto)
                .build();
    }

    @Override
    public ApiResponse<CartDto> getCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        CartDto cartDto = cartMapper.toCartDto(cart);
        return ApiResponse.<CartDto>builder()
                .status(HttpStatus.OK)
                .message("Cart retrieved successfully")
                .data(cartDto)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteCart(UUID cartId, UUID userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        cartRepository.delete(cart);
        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Cart deleted successfully")
                .build();
    }

    @Override
    public ApiResponse<CartItemDto> addItemToCart(CartItemRequest request, UUID userId) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);      
        }
        CartItem cartItem = cartMapper.toCartItemEntity(request);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = cartMapper.toCartItemDto(cartItem);

        return ApiResponse.<CartItemDto>builder()
                .status(HttpStatus.CREATED)
                .message("Item added to cart successfully")
                .data(cartItemDto)
                .build();
    }

    @Override
    public ApiResponse<CartItemDto> updateCartItemQuantity(UUID cartItemId, Integer quantity, UUID userId) {
        if (quantity <= 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }


        Cart cart = cartRepository.findByItemId(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));
        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);  
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = cartMapper.toCartItemDto(cartItem);

        return ApiResponse.<CartItemDto>builder()
                .status(HttpStatus.OK)
                .message("Cart item quantity updated successfully")
                .data(cartItemDto)
                .build();
    }

    @Override
    public ApiResponse<Void> removeItemFromCart(UUID cartItemId, UUID userId) {
        Cart cart = cartRepository.findByItemId(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);  
        }
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);

        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Cart item removed successfully")
                .build();
    }

    @Override
    public ApiResponse<List<CartItemDto>> getCartItems(UUID cartId, UUID userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);  
        }

        List<CartItemDto> cartItemDtos = cartMapper.toCartItemDtoList(cart.getCartItems());

        return ApiResponse.<List<CartItemDto>>builder()
                .status(HttpStatus.OK)
                .message("Cart items retrieved successfully")
                .data(cartItemDtos)
                .build();
    }

    @Override
    public ApiResponse<CartDto> updateCart(UUID cartId, CartRequest request, UUID userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        if (!cart.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Set<ConstraintViolation<CartRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        cartMapper.toCartEntity(request);
        Cart updatedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toCartDto(updatedCart);

        return ApiResponse.<CartDto>builder()
                .status(HttpStatus.OK)
                .message("Cart updated successfully")
                .data(cartDto)
                .build();
    }
}

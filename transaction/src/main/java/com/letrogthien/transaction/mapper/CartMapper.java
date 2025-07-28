package com.letrogthien.transaction.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.CartItemDto;
import com.letrogthien.transaction.entity.Cart;
import com.letrogthien.transaction.entity.CartItem;
import com.letrogthien.transaction.request.CartRequest;
import com.letrogthien.transaction.request.CartItemRequest;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto toCartDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    Cart toCartEntity(CartRequest cartRequest);

    @Mapping(target = "cartId", source = "cart.id")
    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "addedAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CartItem toCartItemEntity(CartItemRequest cartItemRequest);

    List<CartDto> toCartDtoList(List<Cart> carts);

    List<CartItemDto> toCartItemDtoList(List<CartItem> cartItems);
}

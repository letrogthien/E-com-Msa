package com.letrogthien.transaction.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.OrderItemDto;
import com.letrogthien.transaction.entity.Order;
import com.letrogthien.transaction.entity.OrderItem;
import com.letrogthien.transaction.request.OrderRequest;
import com.letrogthien.transaction.request.OrderItemRequest;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderDate", target = "createdAt")
    @Mapping(source = "lastUpdatedAt", target = "updatedAt")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "totalProductsAmount", ignore = true)
    @Mapping(target = "finalAmount", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "orderDate",  ignore = true)
    @Mapping(target = "lastUpdatedAt",ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "paymentRefId", ignore = true)
    @Mapping(target = "paymentGatewayTxnId", ignore = true)
    @Mapping(target = "paymentStatusDetail", constant = "PENDING")
    @Mapping(target = "orderNotes", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "transactionEvents", ignore = true)
    Order toOrderEntity(OrderRequest orderRequest);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "unitPrice", target = "price")
    @Mapping(source = "totalItemPrice", target = "totalAmount")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(source = "price", target = "unitPrice")
    @Mapping(target = "totalItemPrice", ignore = true)
    @Mapping(target = "digitalAssetInfo", ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    OrderItem toOrderItemEntity(OrderItemRequest orderItemRequest);

    List<OrderDto> toOrderDtoList(List<Order> orders);
    List<OrderItemDto> toOrderItemDtoList(List<OrderItem> orderItems);
}

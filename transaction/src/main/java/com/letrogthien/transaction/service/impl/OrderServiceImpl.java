package com.letrogthien.transaction.service.impl;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.OrderItemDto;
import com.letrogthien.transaction.entity.Order;
import com.letrogthien.transaction.exceptions.CustomException;
import com.letrogthien.transaction.exceptions.ErrorCode;
import com.letrogthien.transaction.mapper.OrderMapper;
import com.letrogthien.transaction.repositories.OrderRepository;
import com.letrogthien.transaction.request.OrderRequest;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.OrderService;
import com.letrogthien.transaction.service.TransactionEventLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ApiResponse<OrderDto> createOrder(OrderRequest request) {
        try {

            Order order = orderMapper.toOrderEntity(request);
            Order savedOrder = orderRepository.save(order);
            
            OrderDto orderDto = orderMapper.toOrderDto(savedOrder);
            return ApiResponse.<OrderDto>builder()
                    .status(HttpStatus.CREATED)
                    .message("Order created successfully")
                    .data(orderDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderDto> getOrderById(UUID orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

            OrderDto orderDto = orderMapper.toOrderDto(order);
            return ApiResponse.<OrderDto>builder()
                    .status(HttpStatus.OK)
                    .message("Order retrieved successfully")
                    .data(orderDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderDto>> getMyOrders(UUID buyerId) {
        try {
            List<Order> orders = orderRepository.findByBuyerId(buyerId);
            List<OrderDto> orderDtos = orderMapper.toOrderDtoList(orders);

            return ApiResponse.<List<OrderDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Orders retrieved successfully")
                    .data(orderDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderDto>> getMySellerOrders(UUID sellerId) {
        try {
            List<Order> orders = orderRepository.findBySellerId(sellerId);
            List<OrderDto> orderDtos = orderMapper.toOrderDtoList(orders);

            return ApiResponse.<List<OrderDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Seller orders retrieved successfully")
                    .data(orderDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderDto> updateOrderStatus(UUID orderId, Status newStatus) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
            
            order.setOrderStatus(newStatus);
            Order savedOrder = orderRepository.save(order);
            

            OrderDto orderDto = orderMapper.toOrderDto(savedOrder);
            return ApiResponse.<OrderDto>builder()
                    .status(HttpStatus.OK)
                    .message("Order status updated successfully")
                    .data(orderDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderDto> updatePaymentStatus(UUID orderId, Status paymentStatus) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        
            order.setPaymentStatusDetail(paymentStatus);
            Order savedOrder = orderRepository.save(order);
            OrderDto orderDto = orderMapper.toOrderDto(savedOrder);
            return ApiResponse.<OrderDto>builder()
                    .status(HttpStatus.OK)
                    .message("Payment status updated successfully")
                    .data(orderDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderItemDto>> getOrderItems(UUID orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

            List<OrderItemDto> orderItemDtos = orderMapper.toOrderItemDtoList(order.getOrderItems());
            return ApiResponse.<List<OrderItemDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Order items retrieved successfully")
                    .data(orderItemDtos)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}

package com.letrogthien.transaction.service;

import com.letrogthien.transaction.dto.TransactionEventLogDto;
import com.letrogthien.transaction.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionEventLogService {
    ApiResponse<List<TransactionEventLogDto>> getMyOrderEvents(UUID orderId);
    ApiResponse<TransactionEventLogDto> getEventById(UUID eventId);
}

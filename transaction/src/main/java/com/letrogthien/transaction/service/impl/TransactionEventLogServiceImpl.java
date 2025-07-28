package com.letrogthien.transaction.service.impl;

import com.letrogthien.transaction.dto.TransactionEventLogDto;
import com.letrogthien.transaction.entity.TransactionEventLog;
import com.letrogthien.transaction.exceptions.CustomException;
import com.letrogthien.transaction.exceptions.ErrorCode;
import com.letrogthien.transaction.mapper.TransactionEventLogMapper;
import com.letrogthien.transaction.repositories.TransactionEventLogRepository;
import com.letrogthien.transaction.responses.ApiResponse;
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
public class TransactionEventLogServiceImpl implements TransactionEventLogService {
    private final TransactionEventLogRepository eventLogRepository;
    private final TransactionEventLogMapper eventLogMapper;



    @Override
    public ApiResponse<List<TransactionEventLogDto>> getMyOrderEvents(UUID orderId) {
        try {
            List<TransactionEventLog> logs = eventLogRepository.findByOrderId(orderId);
            List<TransactionEventLogDto> logDtos = eventLogMapper.toDtoList(logs);
            
            return ApiResponse.<List<TransactionEventLogDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Order events retrieved successfully")
                    .data(logDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<TransactionEventLogDto> getEventById(UUID eventId) {
        try {
            TransactionEventLog log = eventLogRepository.findById(eventId)
                    .orElseThrow(() -> new CustomException(ErrorCode.EVENT_LOG_NOT_FOUND));
            
            TransactionEventLogDto logDto = eventLogMapper.toDto(log);
            return ApiResponse.<TransactionEventLogDto>builder()
                    .status(HttpStatus.OK)
                    .message("Event log retrieved successfully")
                    .data(logDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

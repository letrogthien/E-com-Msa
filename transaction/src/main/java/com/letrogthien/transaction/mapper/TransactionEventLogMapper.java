package com.letrogthien.transaction.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.letrogthien.transaction.dto.TransactionEventLogDto;
import com.letrogthien.transaction.entity.TransactionEventLog;

@Mapper(componentModel = "spring")
public interface TransactionEventLogMapper {
    TransactionEventLogMapper INSTANCE = Mappers.getMapper(TransactionEventLogMapper.class);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "description", target = "eventDescription")
    @Mapping(source = "loggedAt", target = "eventTimestamp")
    @Mapping(source = "actorId", target = "createdBy")
    TransactionEventLogDto toDto(TransactionEventLog transactionEventLog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "eventData", ignore = true)
    @Mapping(source = "eventDescription", target = "description")
    @Mapping(target = "loggedAt", ignore = true)
    @Mapping(target = "actorId", ignore = true)
    TransactionEventLog toEntity(TransactionEventLogDto dto);

    List<TransactionEventLogDto> toDtoList(List<TransactionEventLog> logs);
}

package com.letrogthien.wallet.mapper;

import com.letrogthien.wallet.dtos.PaymentTransactionDto;
import com.letrogthien.wallet.entities.PaymentTransaction;
import com.letrogthien.wallet.requests.PaymentTransactionRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentTransactionMapper {

    PaymentTransactionDto toDto(PaymentTransaction paymentTransaction);

    PaymentTransaction toEntity(PaymentTransactionDto paymentTransactionDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status" ,ignore = true)
    PaymentTransaction toEntity(PaymentTransactionRequest createPaymentTransactionRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PaymentTransactionDto paymentTransactionDto, @MappingTarget PaymentTransaction paymentTransaction);
}

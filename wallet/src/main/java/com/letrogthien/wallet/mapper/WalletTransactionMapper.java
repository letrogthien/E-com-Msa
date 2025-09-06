package com.letrogthien.wallet.mapper;

import com.letrogthien.wallet.dtos.WalletTransactionDto;
import com.letrogthien.wallet.entities.WalletTransaction;
import com.letrogthien.wallet.requests.WalletTransactionRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletTransactionMapper {

    WalletTransactionDto toDto(WalletTransaction walletTransaction);

    WalletTransaction toEntity(WalletTransactionDto walletTransactionDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    WalletTransaction toEntity(WalletTransactionRequest createWalletTransactionRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(WalletTransactionDto walletTransactionDto, @MappingTarget WalletTransaction walletTransaction);
}

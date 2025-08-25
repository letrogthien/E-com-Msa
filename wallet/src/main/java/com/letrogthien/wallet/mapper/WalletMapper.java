package com.letrogthien.wallet.mapper;

import com.letrogthien.wallet.dtos.WalletDto;
import com.letrogthien.wallet.entities.Wallet;
import com.letrogthien.wallet.requests.WalletRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {

    WalletDto toDto(Wallet wallet);

    Wallet toEntity(WalletDto walletDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastTransactionId", ignore = true)
    Wallet toEntity(WalletRequest createWalletRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(WalletDto walletDto, @MappingTarget Wallet wallet);
}

package com.letrogthien.wallet.mapper;

import com.letrogthien.wallet.dtos.WithdrawalRequestDto;
import com.letrogthien.wallet.entities.WithdrawalRequestE;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WithdrawalRequestMapper {

    WithdrawalRequestDto toDto(WithdrawalRequestE withdrawalRequest);
}

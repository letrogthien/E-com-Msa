package com.letrogthien.wallet.mapper;

import com.letrogthien.wallet.dtos.DepositRequestDto;
import com.letrogthien.wallet.entities.DepositRequestE;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepositRequestMapper {

    DepositRequestDto toDto(DepositRequestE depositRequest);

}

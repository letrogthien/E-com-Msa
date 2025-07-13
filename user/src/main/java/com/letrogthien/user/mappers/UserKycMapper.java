package com.letrogthien.user.mappers;

import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.entities.UserKyc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserKycMapper {
    @Mapping(
        target = "userId",
        source = "user.id"
    )
    KycDto toDto(UserKyc userKyc);
}

package com.letrogthien.user.mappers;

import com.letrogthien.user.dtos.BillingAddressDto;
import com.letrogthien.user.entities.BillingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillingAddressMapper {
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    BillingAddressDto toDto(BillingAddress entity);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    BillingAddress toEntity(BillingAddressDto dto);

    @Mapping(target = "id", ignore = true)
    void updateBillingAddressFromDto(BillingAddressDto request, @MappingTarget BillingAddress existingAddress);

    List<BillingAddressDto> toDtoList(List<BillingAddress> all);
}
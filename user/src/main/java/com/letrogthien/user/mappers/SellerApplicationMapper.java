package com.letrogthien.user.mappers;


import com.letrogthien.user.dtos.SellerApplicationDto;
import com.letrogthien.user.entities.SellerApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerApplicationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "userKyc.id", target = "userVerificationId") // Corrected from userVerification.id to userKyc.id
    SellerApplicationDto toDto(SellerApplication entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "userKyc", ignore = true) // Corrected from userVerification to userKyc
    @Mapping(target = "reviewerId", ignore = true)
    @Mapping(target = "submissionDate", ignore = true)
    @Mapping(target = "reviewDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SellerApplication toEntity(SellerApplicationDto dto);

    List<SellerApplicationDto> toDtoList(List<SellerApplication> entities);
    List<SellerApplication> toEntityList(List<SellerApplicationDto> dtos);
}
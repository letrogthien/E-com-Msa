package com.letrogthien.user.mappers;


import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.entities.SellerRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerRatingMapper {

    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "transactionId", source = "transaction.id")
    SellerRatingDto toDto(SellerRating rating);
    List<SellerRatingDto> toDtoList(List<SellerRating> ratings);
}

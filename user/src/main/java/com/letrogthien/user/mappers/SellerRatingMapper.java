package com.letrogthien.user.mappers;


import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.entities.SellerRating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerRatingMapper {
    SellerRatingDto toDto(SellerRating rating);
    List<SellerRatingDto> toDtoList(List<SellerRating> ratings);
}

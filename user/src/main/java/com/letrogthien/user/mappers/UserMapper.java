package com.letrogthien.user.mappers;

import com.letrogthien.user.dtos.UserDto;
import com.letrogthien.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "sellerRatingsAsSeller", ignore = true)
    @Mapping(target = "sellerRatingsAsBuyer", ignore = true)
    @Mapping(target = "userVerification", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromDto(UserDto dto, @MappingTarget User user);
}

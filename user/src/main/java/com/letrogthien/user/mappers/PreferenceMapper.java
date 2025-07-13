package com.letrogthien.user.mappers;

import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.entities.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PreferenceMapper {
    PreferenceDto toDto(Preference preference);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(PreferenceDto dto, @MappingTarget Preference preference);
}

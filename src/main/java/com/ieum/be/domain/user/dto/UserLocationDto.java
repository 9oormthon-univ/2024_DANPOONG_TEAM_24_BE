package com.ieum.be.domain.user.dto;

import com.ieum.be.domain.user.entity.Location;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserLocationDto(Long id,
                              @NotNull Double latitude,
                              @NotNull Double longitude,
                              Integer priority) {
    public static UserLocationDto of(Location location) {
        return UserLocationDto.builder()
                .id(location.getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .priority(location.getPriority())
                .build();
    }
}

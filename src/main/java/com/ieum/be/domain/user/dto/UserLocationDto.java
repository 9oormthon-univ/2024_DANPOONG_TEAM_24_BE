package com.ieum.be.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserLocationDto(@NotNull Double latitude,
                              @NotNull Double longitude) {
}

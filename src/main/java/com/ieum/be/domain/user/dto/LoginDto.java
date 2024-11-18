package com.ieum.be.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDto(@NotNull String name,
                       @NotNull String password) {
}

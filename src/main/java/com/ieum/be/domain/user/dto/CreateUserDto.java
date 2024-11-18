package com.ieum.be.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserDto(@NotNull String name,
                            @NotNull String password,
                            String profileUrl) {
}

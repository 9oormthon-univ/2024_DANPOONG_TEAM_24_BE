package com.ieum.be.domain.post.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePostDto(@NotNull String title,
                            @NotNull String content) {
}

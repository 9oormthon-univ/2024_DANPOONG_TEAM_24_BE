package com.ieum.be.domain.comment.dto;

import jakarta.validation.constraints.NotNull;

public record CreateCommentDto(@NotNull Long postId,
                               @NotNull String content) {
}

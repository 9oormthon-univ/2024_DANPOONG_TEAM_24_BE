package com.ieum.be.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ieum.be.domain.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CommentInfoDto {
    @JsonProperty("author")
    private String author;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("content")
    private String content;

    public static CommentInfoDto of(Comment comment) {
        return CommentInfoDto.builder()
                .author(comment.getUser().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

package com.ieum.be.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ieum.be.domain.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CommentInfoDto {
    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("author")
    private String author;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("content")
    private String content;

    public static CommentInfoDto of(Comment comment) {
        return CommentInfoDto.builder()
                .commentId(comment.getCommentId())
                .author(comment.getUser().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

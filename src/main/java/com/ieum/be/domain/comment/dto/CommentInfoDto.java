package com.ieum.be.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ieum.be.domain.comment.entity.Comment;
import com.ieum.be.global.dto.TimeDto;
import lombok.Builder;

@Builder
public class CommentInfoDto {
    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("author")
    private String author;

    @JsonProperty("author_profile_url")
    private String authorProfileUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("content")
    private String content;

    public static CommentInfoDto of(Comment comment) {
        return CommentInfoDto.builder()
                .commentId(comment.getCommentId())
                .author(comment.getUser().getName())
                .authorProfileUrl(comment.getUser().getProfileUrl())
                .content(comment.getContent())
                .createdAt(new TimeDto(comment.getCreatedAt()).toString())
                .build();
    }
}

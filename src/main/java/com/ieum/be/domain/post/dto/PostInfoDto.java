package com.ieum.be.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ieum.be.domain.comment.dto.CommentInfoDto;
import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.post.entity.PostCategory;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class PostInfoDto {
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("author")
    private String author;

    @JsonProperty("author_profile_url")
    private String authorProfileUrl;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("likes")
    private Integer likes;

    @JsonProperty("like_user")
    private List<String> likeUser;
    
    @Setter
    @JsonProperty("liked_by_me")
    private Boolean likedByMe;

    @JsonProperty("comments")
    private List<CommentInfoDto> comments;

    @JsonProperty("post_category")
    private String categoryName;

    public static PostInfoDto of(Post post) {
        return PostInfoDto.builder()
                .postId(post.getId())
                .author(post.getUser().getName())
                .authorProfileUrl(post.getUser().getProfileUrl())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes().size())
                .likeUser(post.getLikes().stream().map(like -> like.getUser().getName()).toList())
                .comments(post.getComments().stream().map(CommentInfoDto::of).toList())
                .categoryName(post.getPostCategory().getCategoryName())
                .build();
    }
}

package com.ieum.be.domain.post.controller;

import com.ieum.be.domain.comment.dto.CommentInfoDto;
import com.ieum.be.domain.post.dto.CreatePostDto;
import com.ieum.be.domain.post.dto.PostInfoDto;
import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.post.service.PostService;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public GeneralResponse createPost(@Valid @RequestBody CreatePostDto createPostDto, Principal principal) {
        return this.postService.createPost(createPostDto, principal.getName());
    }

    @GetMapping("/recent")
    public List<PostInfoDto> getRecentPosts() {
        return this.postService.getRecentPosts();
    }

    @GetMapping("/latest")
    public List<PostInfoDto> getPopularPosts() {
        return this.postService.getPopularPosts();
    }

    @GetMapping("/{postId}")
    public List<CommentInfoDto> getComments(@PathVariable Long postId) {
        return this.postService.getComments(postId);
    }

    @PostMapping("{postId}/like")
    public GeneralResponse likePost(@PathVariable Long postId, Principal principal) {
        return this.postService.likePost(postId, principal.getName());
    }
}

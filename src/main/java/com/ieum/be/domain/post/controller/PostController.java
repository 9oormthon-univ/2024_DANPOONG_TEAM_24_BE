package com.ieum.be.domain.post.controller;

import com.ieum.be.domain.post.dto.CreatePostDto;
import com.ieum.be.domain.post.dto.PostCategoryDto;
import com.ieum.be.domain.post.dto.PostInfoDto;
import com.ieum.be.domain.post.service.PostService;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.validation.Valid;
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
    public List<PostInfoDto> getRecentPosts(Principal principal) {
        return this.postService.getRecentPosts(principal.getName());
    }

    @GetMapping("/popular")
    public List<PostInfoDto> getPopularPosts(Principal principal) {
        return this.postService.getPopularPosts(principal.getName());
    }

    @GetMapping("/{postId}")
    public PostInfoDto getPostInfo(@PathVariable Long postId, Principal principal) {
        return this.postService.getPostInfo(postId, principal.getName());
    }

    @DeleteMapping("/{postId}")
    public GeneralResponse deletePost(@PathVariable Long postId, Principal principal) {
        return this.postService.deletePost(postId, principal.getName());
    }

    @PostMapping("{postId}/like")
    public GeneralResponse likePost(@PathVariable Long postId, Principal principal) {
        return this.postService.likePost(postId, principal.getName());
    }

    @GetMapping("/categories")
    public List<PostCategoryDto> getCategories() {
        return this.postService.getAllCategories();
    }

    @GetMapping("/find/{categoryName}")
    public List<PostInfoDto> findByCategory(@PathVariable String categoryName, Principal principal) {
        return this.postService.findByCategory(categoryName, principal.getName());
    }

    @GetMapping("/get/{type}")
    public List<PostInfoDto> myPost(@PathVariable String type, Principal principal) {
        return this.postService.getMyPost(type, principal.getName());
    }
}

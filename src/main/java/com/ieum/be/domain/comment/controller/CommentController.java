package com.ieum.be.domain.comment.controller;

import com.ieum.be.domain.comment.dto.CreateCommentDto;
import com.ieum.be.domain.comment.service.CommentService;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public GeneralResponse createComment(@Valid @RequestBody CreateCommentDto createCommentDto, @RequestHeader("X-USER-ID") Long userId) {
        return this.commentService.createComment(createCommentDto, userId);
    }
}

package com.ieum.be.domain.comment.service;

import com.ieum.be.domain.comment.dto.CreateCommentDto;
import com.ieum.be.domain.comment.entity.Comment;
import com.ieum.be.domain.comment.repository.CommentRepository;
import com.ieum.be.domain.post.repository.PostRepository;
import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public GeneralResponse createComment(CreateCommentDto createCommentDto, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        Post post = this.postRepository.findById(createCommentDto.postId())
                .orElseThrow(() -> new GlobalException(GeneralResponse.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(createCommentDto.content())
                .build();

        this.commentRepository.save(comment);

        return GeneralResponse.OK;
    }
}

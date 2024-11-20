package com.ieum.be.domain.post.service;

import com.ieum.be.domain.comment.dto.CommentInfoDto;
import com.ieum.be.domain.comment.entity.Comment;
import com.ieum.be.domain.post.entity.Likes;
import com.ieum.be.domain.post.repository.LikeRepository;
import com.ieum.be.domain.post.repository.PostRepository;
import com.ieum.be.domain.post.dto.CreatePostDto;
import com.ieum.be.domain.post.dto.PostInfoDto;
import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public GeneralResponse createPost(CreatePostDto createPostDto, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(createPostDto.title())
                .content(createPostDto.content())
                .build();

        this.postRepository.save(post);

        return GeneralResponse.OK;
    }

    public List<PostInfoDto> getRecentPosts() {
        List<Post> posts = this.postRepository.getTop10ByOrderByCreatedAtDesc();

        return posts.stream().map(PostInfoDto::of).collect(Collectors.toList());
    }

    public List<PostInfoDto> getPopularPosts() {
        List<Post> posts = this.postRepository.getTop10ByOrderByLikesDesc();

        return posts.stream().map(PostInfoDto::of).collect(Collectors.toList());
    }

    public List<CommentInfoDto> getComments(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new GlobalException(GeneralResponse.POST_NOT_FOUND));
        List<Comment> comments = post.getComments();

        return comments.stream().map(CommentInfoDto::of).collect(Collectors.toList());
    }

    public GeneralResponse likePost(Long postId, String email) {
        User user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new GlobalException(GeneralResponse.POST_NOT_FOUND));

        if (this.likeRepository.findLikeByUserEmailAndPostId(email, postId).isPresent()) {
            Likes likes = this.likeRepository.findLikeByUserEmailAndPostId(email, postId).get();

            this.likeRepository.delete(likes);

            return GeneralResponse.OK;
        }

        Likes likes = Likes.builder()
                .user(user)
                .post(post)
                .build();

        this.likeRepository.save(likes);

        return GeneralResponse.OK;
    }
}

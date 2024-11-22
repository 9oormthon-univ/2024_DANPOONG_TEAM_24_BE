package com.ieum.be.domain.post.service;

import com.ieum.be.domain.comment.dto.CommentInfoDto;
import com.ieum.be.domain.comment.entity.Comment;
import com.ieum.be.domain.post.dto.CreatePostDto;
import com.ieum.be.domain.post.dto.PostCategoryDto;
import com.ieum.be.domain.post.dto.PostInfoDto;
import com.ieum.be.domain.post.entity.Likes;
import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.post.entity.PostCategory;
import com.ieum.be.domain.post.repository.LikeRepository;
import com.ieum.be.domain.post.repository.PostCategoryRepository;
import com.ieum.be.domain.post.repository.PostRepository;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostCategoryRepository postCategoryRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository, PostCategoryRepository postCategoryRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    public GeneralResponse createPost(CreatePostDto createPostDto, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        PostCategory postCategory = this.postCategoryRepository.findPostCategoryByCategoryName(createPostDto.categoryName())
                .orElseThrow(() -> new GlobalException(GeneralResponse.POST_CATEGORY_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(createPostDto.title())
                .content(createPostDto.content())
                .postCategory(postCategory)
                .build();

        this.postRepository.save(post);

        return GeneralResponse.OK;
    }

    public List<PostInfoDto> getRecentPosts(String email) {
        List<Post> posts = this.postRepository.getTop30ByOrderByCreatedAtDesc();

        return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
    }

    public List<PostInfoDto> getPopularPosts(String email) {
        List<Post> posts = this.postRepository.getTop2ByOrderByLikesDesc();

        return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).collect(Collectors.toList()), email);
    }

    public PostInfoDto getPostInfo(Long postId, String email) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new GlobalException(GeneralResponse.POST_NOT_FOUND));
        PostInfoDto result = PostInfoDto.of(post);

        result.setLikedByMe(post.getLikes().stream().anyMatch(like -> like.getUser().getEmail().equals(email)));

        return result;
    }

    public GeneralResponse deletePost(Long postId, String email) {
        Post post = this.postRepository.findPostById(postId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.POST_NOT_FOUND));

        if (!post.getUser().getEmail().equals(email)) {
            throw new GlobalException(GeneralResponse.FORBIDDEN);
        }

        this.postRepository.delete(post);

        return GeneralResponse.OK;
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

    public List<PostCategoryDto> getAllCategories() {
        return this.postCategoryRepository.findAll().stream().map(PostCategoryDto::of).toList();
    }

    public List<PostInfoDto> findByCategory(String categoryName, String email) {
        if (categoryName.equals("all")) {
            List<Post> posts = this.postRepository.getAllByPostCategoryNotNull();

            return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
        }

        if (categoryName.equals("popular")) {
            List<Post> posts = this.postRepository.getTop30ByOrderByLikesDesc();

            return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
        }

        PostCategory postCategory = this.postCategoryRepository.findPostCategoryByCategoryName(categoryName).orElse(null);

        if (postCategory == null) {
            throw new GlobalException(GeneralResponse.POST_CATEGORY_NOT_FOUND);
        }

        List<Post> posts = this.postRepository.getPostByPostCategoryOrderByCreatedAtDesc(postCategory);

        return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
    }

    public List<PostInfoDto> getMyPost(String type, String email) {
        List<Post> posts = null;

        switch (type) {
            case "my_post" -> posts = this.postRepository.getPostsByUserEmail(email);

            case "commented" -> {
                posts = this.postRepository.getAllByPostCategoryNotNull()
                        .stream().filter(post -> {
                            List<Comment> comments = post.getComments();

                            for (Comment comment : comments) {
                                if (comment.getUser().getEmail().equals(email)) {
                                    return true;
                                }
                            }

                            return false;
                        }).toList();

                return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
            }

            case "liked" -> {
                posts = this.postRepository.getAllByPostCategoryNotNull()
                        .stream().filter(post -> {
                            List<Likes> likes = post.getLikes();

                            for (Likes like : likes) {
                                if (like.getUser().getEmail().equals(email)) {
                                    return true;
                                }
                            }

                            return false;
                        }).toList();

                System.out.println(posts);
            }

            default -> throw new GlobalException(GeneralResponse.FORBIDDEN);
        }

        return getLikedByMe(posts, posts.stream().map(PostInfoDto::simpleOf).toList(), email);
    }

    private List<PostInfoDto> getLikedByMe(List<Post> posts, List<PostInfoDto> target, String email) {
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostInfoDto postInfoDto = target.get(i);

            postInfoDto.setLikedByMe(this.likeRepository.findLikeByUserEmailAndPostId(email, post.getId()).isPresent());
        }

        return target;
    }
}

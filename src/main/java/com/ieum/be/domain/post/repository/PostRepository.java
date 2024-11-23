package com.ieum.be.domain.post.repository;

import com.ieum.be.domain.post.entity.Post;
import com.ieum.be.domain.post.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(Long postId);
    List<Post> getPostsByUserEmailOrderByCreatedAtDesc(String email);
    List<Post> getAllByPostCategoryNotNullOrderByCreatedAtDesc();
    List<Post> getTop30ByOrderByCreatedAtDesc();
    List<Post> getTop2ByOrderByLikesDescCreatedAtDesc();
    List<Post> getTop30ByOrderByLikesDescCreatedAtDesc();
    List<Post> getPostByPostCategoryOrderByCreatedAtDesc(PostCategory postCategory);
}

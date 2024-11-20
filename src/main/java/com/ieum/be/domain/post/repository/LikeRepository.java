package com.ieum.be.domain.post.repository;

import com.ieum.be.domain.post.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findLikeByUserIdAndPostId(Long userId, Long postId);
}

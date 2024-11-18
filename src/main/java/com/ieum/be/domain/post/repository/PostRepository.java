package com.ieum.be.domain.post.repository;

import com.ieum.be.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getTop10ByOrderByCreatedAtDesc();
    List<Post> getTop10ByOrderByLikesDesc();
}

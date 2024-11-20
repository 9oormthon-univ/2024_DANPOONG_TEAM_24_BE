package com.ieum.be.domain.user.entity;

import com.ieum.be.domain.comment.entity.Comment;
import com.ieum.be.domain.post.entity.Likes;
import com.ieum.be.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Getter
    @Column(name = "user_name")
    private String name;

    @Getter
    @Column(name = "user_email")
    private String email;
    
    @Getter
    @Column(name = "profile_url")
    private String profileUrl;

    @Getter
    @Setter
    @Column(name = "latitude")
    private Double latitude;

    @Getter
    @Setter
    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Likes> likes;
}

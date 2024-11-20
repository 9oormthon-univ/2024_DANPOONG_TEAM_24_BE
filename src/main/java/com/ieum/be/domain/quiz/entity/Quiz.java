package com.ieum.be.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @Column(name = "quiz_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<RestrictedItem> restrictedItem;
}

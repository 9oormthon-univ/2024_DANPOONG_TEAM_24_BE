package com.ieum.be.domain.post.dto;

import com.ieum.be.domain.post.entity.PostCategory;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCategoryDto {
    private Long category_id;
    private String category_name;

    public static PostCategoryDto of(PostCategory postCategory) {
        return PostCategoryDto.builder()
                .category_id(postCategory.getCategoryId())
                .category_name(postCategory.getCategoryName())
                .build();
    }
}

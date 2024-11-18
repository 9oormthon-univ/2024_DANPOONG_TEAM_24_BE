package com.ieum.be.domain.quiz.repository;

import com.ieum.be.domain.quiz.entity.RestrictedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictedItemRepository extends JpaRepository<RestrictedItem, Long> {
}

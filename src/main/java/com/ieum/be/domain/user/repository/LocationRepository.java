package com.ieum.be.domain.user.repository;

import com.ieum.be.domain.user.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUserEmailOrderByPriorityAsc(String email);
}

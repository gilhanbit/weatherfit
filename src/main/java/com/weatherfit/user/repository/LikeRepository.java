package com.weatherfit.user.repository;

import com.weatherfit.user.domain.Like;
import com.weatherfit.user.entity.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Page<LikeEntity> findAllByUserId(int userId, Pageable pageable);
}

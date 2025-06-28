package com.weatherfit.user.repository;

import com.weatherfit.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByLoginId(String loginId);
    public Optional<UserEntity> findByNameAndEmail(String name, String email);
    public Optional<UserEntity> findByLoginIdAndNameAndEmail(String loginId, String name, String email);
}

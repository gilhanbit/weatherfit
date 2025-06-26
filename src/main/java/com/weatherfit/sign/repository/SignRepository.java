package com.weatherfit.sign.repository;

import com.weatherfit.sign.entity.SignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignRepository extends JpaRepository<SignEntity, Integer> {

}

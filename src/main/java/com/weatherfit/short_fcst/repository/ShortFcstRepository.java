package com.weatherfit.short_fcst.repository;

import com.weatherfit.short_fcst.entity.ShortFcstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortFcstRepository extends JpaRepository<ShortFcstEntity, Integer> {

}

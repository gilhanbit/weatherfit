package com.weatherfit.user.mapper;

import com.weatherfit.user.domain.Style;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StyleMapper {

    public Style selectUserStyle(Integer userId);
    public boolean insertUserStyle(int userId, String top, String bottom, String shoes);
    public boolean updateUserStyle(int userId, String top, String bottom, String shoes);
}

package com.weatherfit.user.mapper;

import com.weatherfit.user.domain.Style;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface StyleMapper {

    public Style selectUserStyle(Integer userId);
    public List<String> selectTopChartByGA(String gender, int age);
    public List<String> selectBottomChartByGA(String gender, int age);
    public List<String> selectShoesChartByGA(String gender, int age);

    public List<String> selectTopChartByLocation(int x, int y);
    public List<String> selectBottomChartByLocation(int x, int y);
    public List<String> selectShoesChartByLocation(int x, int y);

    public boolean insertUserStyle(int userId, String top, String bottom, String shoes);
    public boolean updateUserStyle(int userId, String top, String bottom, String shoes);
}

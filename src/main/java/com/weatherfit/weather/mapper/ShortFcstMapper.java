package com.weatherfit.weather.mapper;

import com.weatherfit.weather.domain.ShortFcst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShortFcstMapper {

    public ShortFcst selectTodayFcst(String todayFcst);
    public List<ShortFcst> getShortFcstlist(String x, String y);
    public Boolean findFcstDate(String fcstDate);
    public void updateShortFcst(ShortFcst shortFcst);
    public void insertShortFcst(ShortFcst shortFcst);
}

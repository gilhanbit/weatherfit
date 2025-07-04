package com.weatherfit.weather.mapper;

import com.weatherfit.weather.domain.ShortFcst;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortFcstMapper {

    public Boolean findFcstDate(String fcstDate);
    public void updateShortFcst(ShortFcst shortFcst);
    public void insertShortFcst(ShortFcst shortFcst);
}

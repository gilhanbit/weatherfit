package com.weatherfit.user.mapper;

import com.weatherfit.user.domain.Like;
import com.weatherfit.user.entity.LikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface LikeMapper {

    public int insertProduct(int userId, String link, String image, String title, int lprice);
    public List<Like> selectLikeList10(int userId);
    public List<Like> selectLikeList(int userId);
    public int delProduct(int likeId);
}

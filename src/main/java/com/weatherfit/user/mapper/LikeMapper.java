package com.weatherfit.user.mapper;

import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.entity.LikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface LikeMapper {

    public int insertProduct(int userId, String link, String image, String title, int lprice, String category1, String category2, String category3);
    public List<Like> selectLikeList10(int userId);
    public List<Like> selectLikeList(int userId);
    public int delProduct(int likeId);
    public List<String> selectNearTop(Integer x, Integer y, String topStyle);
    public List<String> selectNearBottom(Integer x, Integer y, String bottomStyle);
    public List<String> selectNearShoes(Integer x, Integer y, String shoesStyle);

}

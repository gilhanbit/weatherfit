package com.weatherfit.user.mapper;

import com.weatherfit.domain.NearTitleSamplingDTO;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.entity.LikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface LikeMapper {

    public int insertProduct(Like like);
    public void insertLikeMeta(int userId, int likeId, String gender, int age, int x, int y, double tmp, String title, int lprice);
    public List<Like> selectLikeList10(int userId);
    public List<Like> selectLikeList(int userId);
    public int delProduct(int likeId);
    public List<NearTitleSamplingDTO> selectNearTop(Integer x, Integer y, String topStyle);
    public List<NearTitleSamplingDTO> selectNearBottom(Integer x, Integer y, String bottomStyle);
    public List<NearTitleSamplingDTO> selectNearShoes(Integer x, Integer y, String shoesStyle);

}

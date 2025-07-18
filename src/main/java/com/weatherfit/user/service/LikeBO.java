package com.weatherfit.user.service;

import com.weatherfit.common.util.GridConverter;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.entity.LikeEntity;
import com.weatherfit.user.mapper.LikeMapper;
import com.weatherfit.user.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeBO {

    private final LikeMapper likeMapper;
    private final LikeRepository likeRepository;

    public int setProduct(int userId, String link, String image, String title, int lprice, String category1, String category2, String category3) {
        return likeMapper.insertProduct(userId, link, image, title, lprice, category1, category2, category3);
    }


    public List<Like> getLikeList10(int userId) {
        return likeMapper.selectLikeList10(userId);
    }


    /**
     * 페이징 X
     * @param userId
     * @return
     */
    public List<Like> getLikeList(int userId) {
        return likeMapper.selectLikeList(userId);
    }


    public int delProduct(int likeId) {
        return likeMapper.delProduct(likeId);
    }


    /**
     * nearStyle
     * @param x
     * @param y
     * @param topStyle
     * @return
     */
    public List<String> getNearTop(Integer x, Integer y, String topStyle) {
        return likeMapper.selectNearTop(x, y, topStyle);
    }


    public List<String> getNearBottom(Integer x, Integer y, String bottomStyle) {
        return likeMapper.selectNearBottom(x, y, bottomStyle);
    }


    public List<String> getNearShoes(Integer x, Integer y, String shoesStyle) {
        return likeMapper.selectNearShoes(x, y, shoesStyle);
    }
}

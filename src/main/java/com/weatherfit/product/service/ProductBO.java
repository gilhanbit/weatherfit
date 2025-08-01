package com.weatherfit.product.service;

import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.naver.service.SearchShopBO;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.service.LikeBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductBO {

    private final SearchShopBO searchShopBO;
    private final LikeBO likeBO;

    public List<SearchShop> getKeywordList(String keyword) {
        return searchShopBO.getKeywordList(keyword);
    }


    public int setProduct(Like like) {
        return likeBO.setProduct(like);
    }


    public void setLikeMeta(int userId, int likeId, String gender, int age, int x, int y, double tmp, String title, int lprice) {
        likeBO.setLikeMeta(userId, likeId, gender, age, x, y, tmp, title, lprice);
    }


    public int delProduct(int likeId) {
        return likeBO.delProduct(likeId);
    }
}

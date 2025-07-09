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


    public int setProduct(int userId, String link, String image, String title, int lprice) {
        return likeBO.setProduct(userId, link, image, title, lprice);
    }


    public int delProduct(int likeId) {
        return likeBO.delProduct(likeId);
    }
}

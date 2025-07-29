package com.weatherfit.user.service;

import com.weatherfit.common.util.GridConverter;
import com.weatherfit.common.util.NearSampling;
import com.weatherfit.common.util.TitleParser;
import com.weatherfit.domain.NearTitleSamplingDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikeBO {

    private final LikeMapper likeMapper;
    private final NearSampling nearSampling;

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
        List<NearTitleSamplingDTO> nearTop = likeMapper.selectNearTop(x, y, topStyle);
        List<String> randomTop = NearSampling.nearTitleSampling(nearTop, 9);

        return TitleParser.keywordFrequency(randomTop);
    }


    public List<String> getNearBottom(Integer x, Integer y, String bottomStyle) {
        List<NearTitleSamplingDTO> nearBottom = likeMapper.selectNearBottom(x, y, bottomStyle);
        List<String> randomBottom = NearSampling.nearTitleSampling(nearBottom, 9);

        return TitleParser.keywordFrequency(randomBottom);
    }


    public List<String> getNearShoes(Integer x, Integer y, String shoesStyle) {
        List<NearTitleSamplingDTO> nearShoes = likeMapper.selectNearShoes(x, y, shoesStyle);
        List<String> randomShoes = NearSampling.nearTitleSampling(nearShoes, 9);

        return TitleParser.keywordFrequency(randomShoes);
    }
}

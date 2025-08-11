package com.weatherfit.user.service;

import com.weatherfit.common.util.TitleParser;
import com.weatherfit.naver.domain.SearchShop;
//import com.weatherfit.service.OllamaBO;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageBO {

    private final UserBO userBO;
    private final StyleBO styleBO;
    private final LikeBO likeBO;
//    private final LikeMetaBO likeMetaBO;
//    private final OllamaBO ollamaBO;

    /**
     * keyword chart by GA
     * @param gender
     * @param age
     * @return
     */
    public LinkedHashMap<String, Double> getTopChartByGA(String gender, int age) {
        return styleBO.getTopChartByGA(gender, age);
    }
    public LinkedHashMap<String, Double> getBottomChartByGA(String gender, int age) {
        return styleBO.getBottomChartByGA(gender, age);
    }
    public LinkedHashMap<String, Double> getShoesChartByGA(String gender, int age) {
        return styleBO.getShoesChartByGA(gender, age);
    }

    /**
     * price chart by GA
     * @param gender
     * @param age
     * @return
     */
    public int getTopPriceChartByGA(String gender, int age) {
        return likeBO.getTopPriceChartByGA(gender, age);
    }
    public int getBottomPriceChartByGA(String gender, int age) {
        return likeBO.getBottomPriceChartByGA(gender, age);
    }
    public int getShoesPriceChartByGA(String gender, int age) {
        return likeBO.getShoesPriceChartByGA(gender, age);
    }

    /**
     * keyword chart by location
     * @param x
     * @param y
     * @return
     */
    public LinkedHashMap<String, Double> getTopChartByLocation(int x, int y) {
        return styleBO.getTopChartByLocation(x, y);
    }
    public LinkedHashMap<String, Double> getBottomChartByLocation(int x, int y) {
        return styleBO.getBottomChartByLocation(x, y);
    }
    public LinkedHashMap<String, Double> getShoesChartByLocation(int x, int y) {
        return styleBO.getShoesChartByLocation(x, y);
    }

    /**
     * price chart by location
     * @param x
     * @param y
     * @return
     */
    public int getTopPriceChartByLocation(int x, int y) {
        return likeBO.getTopPriceChartByLocation(x, y);
    }
    public int getBottomPriceChartByLocation(int x, int y) {
        return likeBO.getBottomPriceChartByLocation(x, y);
    }
    public int getShoesPriceChartByLocation(int x, int y) {
        return likeBO.getShoesPriceChartByLocation(x, y);
    }


    public User getUser(Integer userId) {
        return userBO.getUser(userId);
    }


    public Style getUserStyle(Integer userId) {
        Style isStyle = styleBO.getUserStyle(userId);
        if (isStyle == null) {
            Style style = new Style();
            style.setTop("미지정");
            style.setBottom("미지정");
            style.setShoes("미지정");
            return style;
        }
        return isStyle;
    }


    public List<Like> getLikeList10(int userId) {
        return likeBO.getLikeList10(userId);
    }


    // 관심 리스트 -> LLM 요청
//    public List<SearchShop> recommendKeywords(List<Like> likeList10, int age) throws IOException {
//            // new List
//            List<String> inputKeywords = new ArrayList<>();
//
//            // for -> getTitle
//            for (int i = 0; i < likeList10.size(); i++) {
//                inputKeywords.add(likeList10.get(i).getTitle());
//            }
//
//            inputKeywords = TitleParser.keywordFrequency(inputKeywords);
//
//            return ollamaBO.getRecommendedKeywords(inputKeywords, age);
//    }


    // 콕리스트 (전체)
    public List<Like> getLikeList(int userId) {
        return likeBO.getLikeList(userId);
    }

}

package com.weatherfit.naver.service;

import com.weatherfit.common.OutfitByTemp;
import com.weatherfit.common.util.SearchShopParser;
import com.weatherfit.naver.api.SearchShopAPI;
import com.weatherfit.naver.domain.SearchShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchShopBO {

    private final SearchShopAPI searchShopAPI;

    /**
     * top
     * @param todayTemp
     * @param top
     * @return
     */
    // user
    public List<SearchShop> getUserTopList(Double todayTemp, String top) {

        String topByTemp = OutfitByTemp.topByTemp(todayTemp);

        // 중복 키워드 제거


        String userTop = top + topByTemp;
        String json = searchShopAPI.callNaverSearchShopAPI(userTop);

        return SearchShopParser.parseSearchShop(json);
    }

    // no user
    public List<SearchShop> getTopList(Double todayTemp) {

        String topByTemp = OutfitByTemp.topByTemp(todayTemp);
        String json = searchShopAPI.callNaverSearchShopAPI(topByTemp);

        return SearchShopParser.parseSearchShop(json);
    }


    /**
     * bottom
     * @param todayTemp
     * @param bottom
     * @return
     */
    public List<SearchShop> getUserBottomList(Double todayTemp, String bottom) {

        String bottomByTemp = OutfitByTemp.bottomByTemp(todayTemp);
        String userBottom = bottom + bottomByTemp;
        String json = searchShopAPI.callNaverSearchShopAPI(userBottom);

        return SearchShopParser.parseSearchShop(json);
    }

    public List<SearchShop> getBottomList(Double todayTemp) {

        String bottomByTemp = OutfitByTemp.bottomByTemp(todayTemp);
        String json = searchShopAPI.callNaverSearchShopAPI(bottomByTemp);

        return SearchShopParser.parseSearchShop(json);
    }


    /**
     * shoes
     * @param todayTemp
     * @param shoes
     * @return
     */
    public List<SearchShop> getUserShoesList(Double todayTemp, String shoes) {

        String shoesByTemp = OutfitByTemp.shoesByTemp(todayTemp);
        String userShoes = shoes + shoesByTemp;
        String json = searchShopAPI.callNaverSearchShopAPI(userShoes);

        return SearchShopParser.parseSearchShop(json);
    }

    public List<SearchShop> getShoesList(Double todayTemp) {

        String shoesByTemp = OutfitByTemp.shoesByTemp(todayTemp);
        String json = searchShopAPI.callNaverSearchShopAPI(shoesByTemp);

        return SearchShopParser.parseSearchShop(json);
    }


    public List<SearchShop> getKeywordList(String keyword) {
        String json = searchShopAPI.callNaverSearchShopAPI(keyword);

        return SearchShopParser.parseSearchShop(json);
    }
}

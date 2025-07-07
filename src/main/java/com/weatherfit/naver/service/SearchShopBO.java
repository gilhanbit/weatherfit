package com.weatherfit.naver.service;

import com.weatherfit.naver.api.SearchShopAPI;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.weather.domain.ShortFcst;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchShopBO {

    private final SearchShopAPI searchShopAPI;

    /**
     * 기온+유저 스타일 -> API 요청
     * @param todayTemp
     * @param top
     * @return
     */
    public List<SearchShop> getUserTopList(Double todayTemp, String top) {
        LinkedHashMap<String, String> userStyle = new LinkedHashMap<>();

        int todayFcst = (int)Math.round(todayTemp);

        String topByTemp = null;

        if (todayFcst >= 24) {
            topByTemp = "반팔";
        } else if (todayFcst >= 20) {
            topByTemp = "긴팔티";
        } else if (todayFcst >= 17) {
            topByTemp = "맨투맨";
        } else if (todayFcst >= 11) {
            topByTemp = "자켓";
        } else if (todayFcst >= 6) {
            topByTemp = "자켓";
        } else if (todayFcst >= 0) {
            topByTemp = "패딩";
        } else if (todayFcst < 0) {
            topByTemp = "패딩";
        }


        userStyle.put("top",top+topByTemp);
        String userTop = userStyle.get("top");
        String responseBodyUserTop = searchShopAPI.callNaverSearchShopAPIByUserTop(userTop);


        // TODO - Class
        JSONParser jsonParser = new JSONParser();
        List<SearchShop> topList = new ArrayList<>();

        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseBodyUserTop);
            JSONArray items = (JSONArray) jsonObj.get("items");


            for (int i = 0; i < items.size(); i++) {
                JSONObject obj = (JSONObject) items.get(i);
                SearchShop searchShop = new SearchShop();

                searchShop.setTitle((String) obj.get("title"));
                searchShop.setLink((String) obj.get("link"));
                searchShop.setImage((String) obj.get("image"));
                Integer lprice = Integer.parseInt((String) obj.get("lprice"));
                searchShop.setLprice(lprice);
                topList.add(searchShop);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return topList;
    }


    public List<SearchShop> getTopList(Double todayTemp) {
        LinkedHashMap<String, String> topByTempList = new LinkedHashMap<>();

        int todayFcst = (int)Math.round(todayTemp);

        String topByTemp = null;

        if (todayFcst >= 24) {
            topByTemp = "반팔";
        } else if (todayFcst >= 20) {
            topByTemp = "긴팔티";
        } else if (todayFcst >= 17) {
            topByTemp = "맨투맨";
        } else if (todayFcst >= 11) {
            topByTemp = "자켓";
        } else if (todayFcst >= 6) {
            topByTemp = "자켓";
        } else if (todayFcst >= 0) {
            topByTemp = "패딩";
        } else if (todayFcst < 0) {
            topByTemp = "패딩";
        }


        topByTempList.put("top", topByTemp);
        String noUserTop = topByTempList.get("top");
        String responseBodyTop = searchShopAPI.callNaverSearchShopAPIByTop(noUserTop);


        // TODO - Class
        JSONParser jsonParser = new JSONParser();
        List<SearchShop> topList = new ArrayList<>();

        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseBodyTop);
            JSONArray items = (JSONArray) jsonObj.get("items");


            for (int i = 0; i < items.size(); i++) {
                JSONObject obj = (JSONObject) items.get(i);
                SearchShop searchShop = new SearchShop();

                searchShop.setTitle((String) obj.get("title"));
                searchShop.setLink((String) obj.get("link"));
                searchShop.setImage((String) obj.get("image"));
                Integer lprice = Integer.parseInt((String) obj.get("lprice"));
                searchShop.setLprice(lprice);
                topList.add(searchShop);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return topList;
    }

//    public void getStyle(Double getTodayFcst) {
//        int todayFcst = (int)Math.round(getTodayFcst);
//
//        String topByTemp = null;
//        String bottomByTemp = null;
//        String shoesByTemp = null;
//
//        if (todayFcst >= 24) {
//            topByTemp = "반팔";
//            bottomByTemp = "반바지";
//            shoesByTemp = "여름";
//        } else if (todayFcst >= 20) {
//            topByTemp = "긴팔티";
//            bottomByTemp = "긴바지";
//            shoesByTemp = "여름";
//        } else if (todayFcst >= 17) {
//            topByTemp = "맨투맨";
//            bottomByTemp = "긴바지";
//            shoesByTemp = "여름";
//        } else if (todayFcst >= 11) {
//            topByTemp = "자켓";
//            bottomByTemp = "긴바지";
//            shoesByTemp = "가을";
//        } else if (todayFcst >= 6) {
//            topByTemp = "자켓";
//            bottomByTemp = "긴바지";
//            shoesByTemp = "가을";
//        } else if (todayFcst >= 0) {
//            topByTemp = "패딩";
//            bottomByTemp = "기모바지";
//            shoesByTemp = "겨울";
//        } else if (todayFcst < 0) {
//            topByTemp = "패딩";
//            bottomByTemp = "기모바지";
//            shoesByTemp = "겨울";
//        }
//
//        // API
//        searchShopAPI.callNaverSearchShopAPIByTopByTemp(topByTemp);
//        searchShopAPI.callNaverSearchShopAPIByBottomByTemp(bottomByTemp);
//        searchShopAPI.callNaverSearchShopAPIByShoesByTemp(shoesByTemp);
//    }
//
//
//    /**
//     * API 응답값 파싱
//     */
//    public List<SearchShop> searchTop(String responseBodyUserTop) {
//        JSONParser jsonParser = new JSONParser();
//        List<SearchShop> tops = new ArrayList<>();
//
//        try {
//            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseBodyUserTop);
//            JSONArray items = (JSONArray) jsonObj.get("items"); // response > body > items > item
//
//
//            for (int i = 0; i < items.size(); i++) {
//                JSONObject obj = (JSONObject) items.get(i);
//                SearchShop searchShop = new SearchShop();
//
//                searchShop.setTitle((String) obj.get("title"));
//                searchShop.setLink((String) obj.get("link"));
//                searchShop.setImage((String) obj.get("image"));
//                searchShop.setLprice((Integer) obj.get("lprice"));
//                searchShop.setHprice((Integer) obj.get("hprice"));
//
//                tops.add(searchShop);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return tops;
//    }
}

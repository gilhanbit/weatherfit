package com.weatherfit.common.util;

import com.weatherfit.naver.domain.SearchShop;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchShopParser {

    public static List<SearchShop> parseSearchShop(String json) {
        JSONParser parser = new JSONParser();
        List<SearchShop> list = new ArrayList<>();

        try {
            JSONObject jsonObj = (JSONObject) parser.parse(json);
            JSONArray items = (JSONArray) jsonObj.get("items");

            for (Object item : items) {
                JSONObject obj = (JSONObject) item;
                SearchShop shop = new SearchShop();
                shop.setTitle((String) obj.get("title"));
                shop.setLink((String) obj.get("link"));
                shop.setImage((String) obj.get("image"));
                shop.setLprice(Integer.parseInt((String) obj.get("lprice")));
                shop.setCategory1((String) obj.get("category1"));
                shop.setCategory2((String) obj.get("category2"));
                shop.setCategory3((String) obj.get("category3"));
                list.add(shop);
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }

        return list;
    }
}

package com.weatherfit.main.service;

import com.weatherfit.common.excel.LocationDataParser;
import com.weatherfit.common.util.GridConverter;
import com.weatherfit.common.util.TitleParser;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.naver.service.SearchShopBO;
import com.weatherfit.user.service.LikeBO;
import com.weatherfit.user.service.UserBO;
import com.weatherfit.weather.api.ShortFcstAPI;
import com.weatherfit.weather.domain.ShortFcst;
import com.weatherfit.weather.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainBO {

    private final ShortFcstBO shortFcstBO;
    private final ShortFcstAPI shortFcstAPI;
    private final SearchShopBO searchShopBO;
    private final LocationDataParser locationDataParser;
    private final UserBO userBO;
    private final LikeBO likeBO;

    public String dayFormat() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return today.format(formatter);
    }


    public List<String> getNext7DaysInKorean() {
        List<String> weekList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            LocalDate day = LocalDate.now().plusDays(i);
            DayOfWeek dayOfWeek = day.getDayOfWeek();
            String koreanDayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, new Locale("ko", "KR"));
            weekList.add(koreanDayOfWeek);
        }
        return weekList;
    }

    /**
     * weather
     * @param todayFcst
     * @return
     */
    public ShortFcst getTodayFcst(String todayFcst) {
        return shortFcstBO.getTodayFcst(todayFcst);
    }


    // TODO x, y 서버에 존재 시 select로 불러와 (desc -> list reverse) 뿌리고, 없으면 API 호출
    public List<ShortFcst> getShortFcstlist(Double lat, Double lon) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        String x = gridCoord.nx + "";
        String y = gridCoord.ny + "";
        return shortFcstBO.getShortFcstlist(x, y);
    }


    public void setShortFcst(Double lat, Double lon) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        String x = gridCoord.nx + "";
        String y = gridCoord.ny + "";

        LocalDate day = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = day.format(formatter);

        try {
            String result = shortFcstAPI.callShortFcstAPI(x, y, today);
            shortFcstBO.setShortFcst(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * user location
     * @param lat
     * @param lon
     * @return
     */
    public String getLocation(Double lat, Double lon) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        String x = gridCoord.nx + "";
        String y = gridCoord.ny + "";

        String location = locationDataParser.userLocation(x, y);
        return location;
    }


    public void setLocation(Integer userId, Double lat, Double lon) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        Integer x = gridCoord.nx;
        Integer y = gridCoord.ny;

        userBO.setLocation(userId, x, y);
    }


    /**
     * nearStyle
     */
    public List<String> getNearTop(Double lat, Double lon, String topStyle) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        Integer x = gridCoord.nx;
        Integer y = gridCoord.ny;
        List<String> nearTop = likeBO.getNearTop(x, y, topStyle);
        Collections.shuffle(nearTop);
        List<String> randomTop = nearTop.stream()
                .limit(9)
                .collect(Collectors.toList());
        List<String> nearTopTitle = TitleParser.keywordFrequency(randomTop);

        return nearTopTitle;
    }


    public List<String> getNearBottom(Double lat, Double lon, String bottomStyle) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        Integer x = gridCoord.nx;
        Integer y = gridCoord.ny;
        List<String> nearBottom = likeBO.getNearBottom(x, y, bottomStyle);
        Collections.shuffle(nearBottom);
        List<String> randomBottom = nearBottom.stream()
                .limit(9)
                .collect(Collectors.toList());
        List<String> nearBottomTitle = TitleParser.keywordFrequency(randomBottom);

        return nearBottomTitle;
    }


    public List<String> getNearShoes(Double lat, Double lon, String shoesStyle) {
        GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
        Integer x = gridCoord.nx;
        Integer y = gridCoord.ny;
        List<String> nearShoes = likeBO.getNearShoes(x, y, shoesStyle);
        Collections.shuffle(nearShoes);
        List<String> randomShoes = nearShoes.stream()
                .limit(9)
                .collect(Collectors.toList());
        List<String> nearShoesTitle = TitleParser.keywordFrequency(randomShoes);

        return nearShoesTitle;
    }


    /**
     * style
     * @param todayTemp
     * @param topStyle
     * @return
     */
    // top
    public List<SearchShop> getUserTopList(Double todayTemp, String topStyle, List<String> nearTop) {
        return searchShopBO.getUserTopList(todayTemp, topStyle, nearTop);
    }

    public List<SearchShop> getTopList(Double todayTemp) {
        return searchShopBO.getTopList(todayTemp);
    }


    // bottom
    public List<SearchShop> getUserBottomList(Double todayTemp, String bottomStyle, List<String> nearBottom) {
        return searchShopBO.getUserBottomList(todayTemp, bottomStyle, nearBottom);
    }

    public List<SearchShop> getBottomList(Double todayTemp) {
        return searchShopBO.getBottomList(todayTemp);
    }


    // shoes
    public List<SearchShop> getUserShoesList(Double todayTemp, String shoesStyle, List<String> nearShoes) {
        return searchShopBO.getUserShoesList(todayTemp, shoesStyle, nearShoes);
    }

    public List<SearchShop> getShoesList(Double todayTemp) {
        return searchShopBO.getShoesList(todayTemp);
    }
}

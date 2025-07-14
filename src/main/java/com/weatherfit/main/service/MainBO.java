package com.weatherfit.main.service;

import com.weatherfit.common.excel.LocationDataParser;
import com.weatherfit.common.util.GridConverter;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.naver.service.SearchShopBO;
import com.weatherfit.weather.api.ShortFcstAPI;
import com.weatherfit.weather.domain.ShortFcst;
import com.weatherfit.weather.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBO {

    private final ShortFcstBO shortFcstBO;
    private final ShortFcstAPI shortFcstAPI;
    private final SearchShopBO searchShopBO;
    private final LocationDataParser locationDataParser;


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


    /**
     * style
     * @param todayTemp
     * @param topStyle
     * @return
     */
    // top
    public List<SearchShop> getUserTopList(Double todayTemp, String topStyle) {
        return searchShopBO.getUserTopList(todayTemp, topStyle);
    }

    public List<SearchShop> getTopList(Double todayTemp) {
        return searchShopBO.getTopList(todayTemp);
    }


    // bottom
    public List<SearchShop> getUserBottomList(Double todayTemp, String bottomStyle) {
        return searchShopBO.getUserBottomList(todayTemp, bottomStyle);
    }

    public List<SearchShop> getBottomList(Double todayTemp) {
        return searchShopBO.getBottomList(todayTemp);
    }


    // shoes
    public List<SearchShop> getUserShoesList(Double todayTemp, String shoesStyle) {
        return searchShopBO.getUserShoesList(todayTemp, shoesStyle);
    }

    public List<SearchShop> getShoesList(Double todayTemp) {
        return searchShopBO.getShoesList(todayTemp);
    }
}

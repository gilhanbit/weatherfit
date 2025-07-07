package com.weatherfit.main.service;

import com.weatherfit.common.GridConverter;
import com.weatherfit.weather.api.ShortFcstAPI;
import com.weatherfit.weather.domain.ShortFcst;
import com.weatherfit.weather.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBO {

    private final ShortFcstBO shortFcstBO;
    private final ShortFcstAPI shortFcstAPI;

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
}

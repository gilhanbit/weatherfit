package com.weatherfit.main.service;

import com.weatherfit.common.GridConverter;
import com.weatherfit.short_fcst.ShortFcstAPI;
import com.weatherfit.short_fcst.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MainBO {

    private final ShortFcstBO shortFcstBO;

    public void shortFcst(Double lat, Double lon) {
        try {
            GridConverter.GridCoord gridCoord = GridConverter.convertToGrid(lat, lon);
            int x = gridCoord.nx;
            int y = gridCoord.ny;
            String result = ShortFcstAPI.callShortFcstAPI(x, y);
            shortFcstBO.setShortFcst(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.weatherfit.main.service;

import com.weatherfit.short_fcst.entity.ShortFcstEntity;
import com.weatherfit.short_fcst.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainBO {

    private final ShortFcstBO shortFcstBO;
//    private final MidFcstBO midFcstBO;

    public List<ShortFcstEntity> shortFcst() {
        return shortFcstBO.getStoredForecasts();
    }
}

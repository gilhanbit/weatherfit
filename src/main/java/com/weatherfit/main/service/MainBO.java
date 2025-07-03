package com.weatherfit.main.service;

import com.weatherfit.short_fcst.service.ShortFcstBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainBO {

    private final ShortFcstBO shortFcst;
}

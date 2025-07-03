package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import com.weatherfit.short_fcst.ShortFcstAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/weatherfit")
@Controller
public class MainController {

    private final MainBO mainBO;

    @GetMapping("/main")
    public String mainPage(
            @RequestParam(value="lat", required = false) Double lat,
            @RequestParam(value="lon", required = false) Double lon,
            Model model
    ) {

        // TODO 클라 nx, ny 좌표 받아서 API에 삽입 -> 내 서버 저장 -> 좌표와 일치하는 최신 데이터 4개 호출 -> 응답 값 model에 담아 뿌리기
        if (lat != null && lon != null) {
            mainBO.shortFcst(lat, lon);
        } else {
            mainBO.shortFcst(Double.valueOf(61), Double.valueOf(126));
        }

        return "main/mainPage";
    }
}

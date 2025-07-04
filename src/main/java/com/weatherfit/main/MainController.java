package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

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
            List<LinkedHashMap<String, Object>> getShortFcst = mainBO.getShortFcst(lat, lon);

            if (getShortFcst == null) {
                mainBO.setShortFcst(lat, lon);
            }
        } else {
            mainBO.setShortFcst(37.497979, 127.027616);
        }

        return "main/mainPage";
    }
}

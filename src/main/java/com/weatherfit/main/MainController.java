package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.weather.domain.ShortFcst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RequestMapping("/weatherfit")
@Controller
public class MainController {

    private final MainBO mainBO;

    @GetMapping("/main")
    public String mainPage(
            @RequestParam(value="lat", required = false) Double lat,
            @RequestParam(value="lon", required = false) Double lon,
            Model model,
            HttpSession session
    ) {

        /**
         * 기상 Data
         */

        // 오늘 날짜로 요일 계산
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String todayFcst = today.format(formatter);
        List<String> weekList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            LocalDate day = LocalDate.now().plusDays(i);
            DayOfWeek dayOfWeek = day.getDayOfWeek();
            String koreanDayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, new Locale("ko", "KR"));

            weekList.add(koreanDayOfWeek);
        }

        model.addAttribute("weekList", weekList);

        // TODO 클라 nx, ny 좌표 받아서 API에 삽입 -> 내 서버 저장 -> 좌표와 일치하는 최신 데이터 3개 호출 -> 응답 값 model에 담아 뿌리기
        if (lat != null && lon != null) {
            ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
            model.addAttribute("todayFcst" ,getTodayFcst);

            List<ShortFcst> getShortFcstlist = mainBO.getShortFcstlist(lat, lon);
            model.addAttribute("shortFcstList", getShortFcstlist);

            String getUserLocation = mainBO.getLocation(lat, lon);
            model.addAttribute("userLocation", getUserLocation);

            if (getShortFcstlist == null) {
                mainBO.setShortFcst(lat, lon);
            }
        } else if (lat == null || lon == null) {
            mainBO.setShortFcst(37.497979, 127.027616);

            ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
            model.addAttribute("todayFcst" ,getTodayFcst);

            List<ShortFcst> getShortFcstlist = mainBO.getShortFcstlist(37.497979, 127.027616);
            model.addAttribute("shortFcstList", getShortFcstlist);

            String getUserLocation = mainBO.getLocation(37.497979, 127.027616);
            model.addAttribute("userLocation", getUserLocation);
        }


        /**
         * 스타일 Data
         */
        Integer userId = (Integer)session.getAttribute("userId");
        String topStyle = (String)session.getAttribute("top");
        String bottomStyle = (String)session.getAttribute("bottom");
        String shoesStyle = (String)session.getAttribute("shoes");


        ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
        Double todayTemp = getTodayFcst.getTmp();


        // 로그인 (기온+스타일)
        if (topStyle != null && bottomStyle != null && shoesStyle != null) {
            // API 호출
            List<SearchShop> userTopList = mainBO.getUserTopList(todayTemp, topStyle);
            List<SearchShop> userBottomList = mainBO.getUserBottomList(todayTemp, bottomStyle);
            List<SearchShop> userShoesList = mainBO.getUserShoesList(todayTemp, shoesStyle);

            // main
            SearchShop mainTop = userTopList.get(0);
            SearchShop mainBottom = userBottomList.get(0);
            SearchShop mainShoes = userShoesList.get(0);
            model.addAttribute("mainTop", mainTop);
            model.addAttribute("mainBottom", mainBottom);
            model.addAttribute("mainShoes", mainShoes);

            // like list

        } else {
            // API 호출
            List<SearchShop> topList = mainBO.getTopList(todayTemp);
            List<SearchShop> bottomList = mainBO.getBottomList(todayTemp);
            List<SearchShop> shoesList = mainBO.getShoesList(todayTemp);

            // main
            SearchShop mainTop = topList.get(0);
            SearchShop mainBottom = bottomList.get(0);
            SearchShop mainShoes = shoesList.get(0);
            model.addAttribute("mainTop", mainTop);
            model.addAttribute("mainBottom", mainBottom);
            model.addAttribute("mainShoes", mainShoes);
        }
        return "main/mainPage";
    }
}

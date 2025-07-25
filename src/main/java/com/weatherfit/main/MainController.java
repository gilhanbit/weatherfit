package com.weatherfit.main;

import com.weatherfit.common.util.TitleParser;
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

        Integer userId = (Integer) session.getAttribute("userId");


        // TODO 클라 nx, ny 좌표 받아서 API에 삽입 -> 내 서버 저장 -> 좌표와 일치하는 최신 데이터 3개 호출 -> 응답 값 model에 담아 뿌리기
        if (lat != null && lon != null) {

            if (userId != null) {
                mainBO.setLocation(userId, lat, lon);
            }

            String getUserLocation = mainBO.getLocation(lat, lon);
            session.setAttribute("userLocation", getUserLocation);
            model.addAttribute("userLocation", getUserLocation);


            ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
            model.addAttribute("todayFcst" ,getTodayFcst);


            List<ShortFcst> getShortFcstlist = mainBO.getShortFcstlist(lat, lon);
            model.addAttribute("shortFcstList", getShortFcstlist);


            if (getShortFcstlist == null) {
                mainBO.setShortFcst(lat, lon);
            }


        } else if (lat == null || lon == null) {

            if (userId != null) {
                mainBO.setLocation(userId, 37.497979, 127.027616);
            }


            String getUserLocation = mainBO.getLocation(37.497979, 127.027616);
            session.setAttribute("userLocation", getUserLocation);
            model.addAttribute("userLocation", getUserLocation);


            mainBO.setShortFcst(37.497979, 127.027616);


            ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
            model.addAttribute("todayFcst" ,getTodayFcst);


            List<ShortFcst> getShortFcstlist = mainBO.getShortFcstlist(37.497979, 127.027616);
            model.addAttribute("shortFcstList", getShortFcstlist);
        }


        /**
         * 스타일 Data
         */
        String topStyle = (String)session.getAttribute("top");
        String bottomStyle = (String)session.getAttribute("bottom");
        String shoesStyle = (String)session.getAttribute("shoes");


        ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
        Double todayTemp = getTodayFcst.getTmp();


        // 로그인 (기온+스타일)
        if (topStyle != null && bottomStyle != null && shoesStyle != null) {
            // API 호출
            // main


            // like list

        }


        // TODO nearTOP title keyword 추출 -> 빈도수 높은 키워드 + 유저 스타일 키워드 + 기온에 맞는 차림 -> API 요청
        // 주변 동일 스타일 랜덤 9명의 콕리스트에 따른 상품 출력
        if (lat != null && lon != null && topStyle != null && bottomStyle != null && shoesStyle != null) {
            // 상의
            List<String> nearTop = mainBO.getNearTop(lat, lon, topStyle);
            List<SearchShop> userTopList = mainBO.getUserTopList(todayTemp, topStyle, nearTop);


            // 하의
            List<String> nearBottom = mainBO.getNearBottom(lat, lon, bottomStyle);
            List<SearchShop> userBottomList = mainBO.getUserBottomList(todayTemp, bottomStyle);


            // 신발
            List<String> nearShoes = mainBO.getNearShoes(lat, lon, shoesStyle);
            List<SearchShop> userShoesList = mainBO.getUserShoesList(todayTemp, shoesStyle);


            SearchShop mainTop = userTopList.get(0);
            SearchShop mainBottom = userBottomList.get(0);
            SearchShop mainShoes = userShoesList.get(0);
            model.addAttribute("mainTop", mainTop);
            model.addAttribute("mainBottom", mainBottom);
            model.addAttribute("mainShoes", mainShoes);
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

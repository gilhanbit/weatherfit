package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.weather.domain.ShortFcst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.Duration;
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
    private final RedisTemplate<String, Object> redisTemplate;


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
        String todayFcst = mainBO.dayFormat();
        List<String> weekList = mainBO.getNext7DaysInKorean();
        model.addAttribute("weekList", weekList);

        Integer userId = (Integer) session.getAttribute("userId");


        String key;
        Duration ttl;

        if (userId != null) {
            String guestKey = "shortFcst:guest:" + lat + ":" + lon;
            redisTemplate.delete(guestKey);

            key = "shortFcst:user:" + userId + ":" + lat + ":" + lon;
            ttl = Duration.ofMinutes(15);
        } else {
            key = "shortFcst:guest:" + lat + ":" + lon;
            ttl = Duration.ofMinutes(3);
        }


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

            lat = 37.496943;
            lon = 127.062948;

            if (userId != null) {
                mainBO.setLocation(userId, lat, lon);
            }


            String getUserLocation = mainBO.getLocation(lat, lon);
            session.setAttribute("userLocation", getUserLocation);
            model.addAttribute("userLocation", getUserLocation);


            mainBO.setShortFcst(lat, lon);


            ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
            model.addAttribute("todayFcst" ,getTodayFcst);


            List<ShortFcst> getShortFcstlist = mainBO.getShortFcstlist(lat, lon);
            model.addAttribute("shortFcstList", getShortFcstlist);
        }

        model.addAttribute("lat", lat);
        model.addAttribute("lon", lon);

        /**
         * 스타일 Data
         */
        String topStyle = (String)session.getAttribute("top");
        String bottomStyle = (String)session.getAttribute("bottom");
        String shoesStyle = (String)session.getAttribute("shoes");


        ShortFcst getTodayFcst = mainBO.getTodayFcst(todayFcst);
        Double todayTemp = getTodayFcst.getTmp();


        // TODO nearTOP title keyword 추출 -> 빈도수 높은 키워드 + 유저 스타일 키워드 + 기온에 맞는 차림 -> API 요청
        // 주변 동일 스타일 랜덤 9명의 콕리스트에 따른 상품 출력
        if (topStyle != null && bottomStyle != null && shoesStyle != null) {
            // 상의
            List<String> nearTop = mainBO.getNearTop(lat, lon, topStyle);
            List<SearchShop> userTopList = mainBO.getUserTopList(todayTemp, topStyle, nearTop);


            // 하의
            List<String> nearBottom = mainBO.getNearBottom(lat, lon, bottomStyle);
            List<SearchShop> userBottomList = mainBO.getUserBottomList(todayTemp, bottomStyle, nearBottom);


            // 신발
            List<String> nearShoes = mainBO.getNearShoes(lat, lon, shoesStyle);
            List<SearchShop> userShoesList = mainBO.getUserShoesList(todayTemp, shoesStyle, nearShoes);


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

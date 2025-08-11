package com.weatherfit.user;

import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.user.domain.Like;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import com.weatherfit.user.service.MypageBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final MypageBO mypageBO;

    // U: pw, style (클라가 입력할 경우)
    // R: user, style, like(limit 10)
    @PostMapping("/mypage")
    public String mypage() {
        return "redirect:/user/remypage";
    }


    @GetMapping("/remypage")
    public String remypage(
            Model model,
            HttpSession session) throws IOException {

        String gender = (String) session.getAttribute("userGender");
        int age = (int) session.getAttribute("userAge");
        int x = (int) session.getAttribute("userX");
        int y = (int) session.getAttribute("userY");


        LinkedHashMap<String, Double> topChartByGA = mypageBO.getTopChartByGA(gender, age);
        LinkedHashMap<String, Double> bottomChartByGA = mypageBO.getBottomChartByGA(gender, age);
        LinkedHashMap<String, Double> shoesChartByGA = mypageBO.getShoesChartByGA(gender, age);
        model.addAttribute("topChartByGA", topChartByGA);
        model.addAttribute("bottomChartByGA", bottomChartByGA);
        model.addAttribute("shoesChartByGA", shoesChartByGA);

        int topPriceChartByGA = mypageBO.getTopPriceChartByGA(gender, age);
        int bottomPriceChartByGA = mypageBO.getBottomPriceChartByGA(gender, age);
        int shoesPriceChartByGA = mypageBO.getShoesPriceChartByGA(gender, age);
        model.addAttribute("topPriceChartByGA", topPriceChartByGA);
        model.addAttribute("bottomPriceChartByGA", bottomPriceChartByGA);
        model.addAttribute("shoesPriceChartByGA", shoesPriceChartByGA);

        LinkedHashMap<String, Double> topChartByLocation = mypageBO.getTopChartByLocation(x, y);
        LinkedHashMap<String, Double> bottomChartByLocation = mypageBO.getBottomChartByLocation(x, y);
        LinkedHashMap<String, Double> shoesChartByLocation = mypageBO.getShoesChartByLocation(x, y);
        model.addAttribute("topChartByLocation", topChartByLocation);
        model.addAttribute("bottomChartByLocation", bottomChartByLocation);
        model.addAttribute("shoesChartByLocation", shoesChartByLocation);

        int topPriceChartByLocation = mypageBO.getTopPriceChartByLocation(x, y);
        int bottomPriceChartByLocation = mypageBO.getBottomPriceChartByLocation(x, y);
        int shoesPriceChartByLocation = mypageBO.getShoesPriceChartByLocation(x, y);
        model.addAttribute("topPriceChartByLocation", topPriceChartByLocation);
        model.addAttribute("bottomPriceChartByLocation", bottomPriceChartByLocation);
        model.addAttribute("shoesPriceChartByLocation", shoesPriceChartByLocation);

        User user = mypageBO.getUser((Integer)session.getAttribute("userId"));
        model.addAttribute("user", user);

        Style style = mypageBO.getUserStyle((Integer)session.getAttribute("userId"));
        model.addAttribute("style", style);

        List<Like> likeList10 = mypageBO.getLikeList10((Integer)session.getAttribute("userId"));
        model.addAttribute("likeList10", likeList10);


        // like 10개 -> title 파싱 -> LLM 요청
//        List<SearchShop> recommendKeywords =  mypageBO.recommendKeywords(likeList10, age);
//        model.addAttribute("recommendKeywords", recommendKeywords);

        return "user/mypage";
    }


    // 전체 콕리스트
    @GetMapping("/like")
    public String like(
            Model model,
            HttpSession session
    ) {

        User user = mypageBO.getUser((Integer)session.getAttribute("userId"));
        List<Like> likeList = mypageBO.getLikeList((Integer) session.getAttribute("userId"));

        model.addAttribute("user", user);
        model.addAttribute("likeList", likeList);

        return "like/like";
    }

}

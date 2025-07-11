package com.weatherfit.product;

import com.weatherfit.naver.domain.SearchShop;
import com.weatherfit.product.service.ProductBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductBO productBO;

    @GetMapping("/search-list")
    public String searchList(
            @RequestParam("keyword") String keyword,
            HttpSession session,
            Model model
    ){

        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("userId", userId);

        List<SearchShop> keywordList = productBO.getKeywordList(keyword);
        model.addAttribute("keywordList", keywordList);
        return "product/searchList";
    }

}

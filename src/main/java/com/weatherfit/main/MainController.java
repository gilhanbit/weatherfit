package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import com.weatherfit.short_fcst.entity.ShortFcstEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/weatherfit")
@Controller
public class MainController {

    private final MainBO mainBO;

    @GetMapping("/main")
    public String mainPage(Model model){

        List<ShortFcstEntity> shortFcst = mainBO.shortFcst();
        model.addAttribute("shortFcst", shortFcst);



        return "main/mainPage";
    }
}

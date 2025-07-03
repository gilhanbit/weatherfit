package com.weatherfit.main;

import com.weatherfit.main.service.MainBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/weatherfit")
@Controller
public class MainController {

    private final MainBO mainBO;

    @GetMapping("/main")
    public String mainPage(Model model){


        return "main/mainPage";
    }
}

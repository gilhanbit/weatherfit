package com.weatherfit.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/weatherfit")
@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(){
        return "main/mainPage";
    }
}

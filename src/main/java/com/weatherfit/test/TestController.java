package com.weatherfit.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test1")
    public String test1(){
        return "<h2>test</h2>";
    }

    @GetMapping("/test2")
    public String test2(){
        return "test/test";
    }
}

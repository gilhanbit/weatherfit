package com.weatherfit.sign;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sign")
@Controller
public class SignController {

    @GetMapping("/sign-in")
    public String signIn(){
        return "sign/signIn";
    }

    @GetMapping("/sign-up")
    public String signUp(){
        return "sign/signUp";
    }
}

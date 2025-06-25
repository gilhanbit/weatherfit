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

    @GetMapping("/sign-out")
    public String signOut(){
        return "redirect:/main/mainPage";
    }

    @GetMapping("/sign-up")
    public String signUp(){
        return "sign/signUp";
    }

    @GetMapping("/help-id")
    public String helpId(){
        return "sign/helpId";
    }

    @GetMapping("/check-id")
    public String checkId(){
        return "sign/checkId";
    }

    @GetMapping("/help-pw")
    public String helpPw(){
        return "sign/helpPw";
    }

    @GetMapping("/extra-pw")
    public String extraPw(){
        return "sign/extraPw";
    }
}

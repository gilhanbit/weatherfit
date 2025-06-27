package com.weatherfit.sign;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/sign")
@Controller
public class SignController {

    @GetMapping("/sign-in")
    public String signIn(){
        return "sign/signIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("userLoginId");

        return "redirect:/weatherfit/main";
    }

    @GetMapping("/sign-up")
    public String signUp(){
        return "sign/signUp";
    }

    @GetMapping("/help-id")
    public String helpId(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         Model model
    ){

        return "redirect:/sign/helpId";
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

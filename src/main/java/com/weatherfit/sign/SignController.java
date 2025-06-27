package com.weatherfit.sign;

import com.weatherfit.sign.service.SignBO;
import com.weatherfit.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/sign")
@Controller
public class SignController {

    private final SignBO signBO;

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
    public String helpId(){
        return "sign/helpId";
    }

    // TODO 비동기 요청으로 수정할 것
    @PostMapping("/check")
    public String helpId(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model
    ){

        UserEntity user = signBO.findUserLoginId(name, email);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "sign/checkId";
    }

    @GetMapping("/help-pw")
    public String helpPw(
            @RequestParam("loginId") String loginId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model
    ){

        String newPwd = RandomStringUtils.randomAlphanumeric(10);

        return "sign/helpPw";
    }

    @PostMapping("/extra-pw")
    public String extraPw(){
        return "sign/extraPw";
    }
}

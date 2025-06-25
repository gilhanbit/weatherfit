package com.weatherfit.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

    @GetMapping("/mypage")
    public String mypage(){
        return "user/mypage";
    }

    @GetMapping("/like")
    public String like(){
        return "user/like";
    }
}

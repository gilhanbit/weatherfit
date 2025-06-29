package com.weatherfit.user;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
public class UserController {

    // U: pw, style (클라가 입력할 경우)
    // R: user, style, like(limit 10)
    @PostMapping("/mypage")
    public String mypage(Model model) {
        return "user/mypage";
    }

    @PostMapping("/like")
    public String like() {
        return "user/like";
    }
}

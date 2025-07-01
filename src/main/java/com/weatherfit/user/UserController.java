package com.weatherfit.user;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.user.domain.MypageDTO;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import com.weatherfit.user.entity.UserEntity;
import com.weatherfit.user.service.MypageBO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final MypageBO mypageBO;

    // U: pw, style (클라가 입력할 경우)
    // R: user, style, like(limit 10)
    @PostMapping("/mypage")
    public String mypage(
            Model model,
            HttpSession session) {

        User user = mypageBO.getUser((Integer)session.getAttribute("userId"));
        model.addAttribute("user", user);

        Style style = mypageBO.getUserStyle((Integer)session.getAttribute("userId"));
        model.addAttribute("style", style);

//        List<MypageDTO> mypageDTOList = mypageBO.generateUserLike((Integer)session.getAttribute("userId"));
//        model.addAttribute("myPage", mypageDTOList);
        return "user/mypage";
    }
}

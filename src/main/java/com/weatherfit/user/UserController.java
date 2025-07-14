package com.weatherfit.user;

import com.weatherfit.user.domain.Like;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import com.weatherfit.user.entity.LikeEntity;
import com.weatherfit.user.service.MypageBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

        List<Like> likeList10 = mypageBO.getLikeList10((Integer)session.getAttribute("userId"));
        model.addAttribute("likeList10", likeList10);

        return "redirect:/user/remypage";
    }


    @GetMapping("/remypage")
    public String remypage(
            Model model,
            HttpSession session) {

        User user = mypageBO.getUser((Integer)session.getAttribute("userId"));
        model.addAttribute("user", user);

        Style style = mypageBO.getUserStyle((Integer)session.getAttribute("userId"));
        model.addAttribute("style", style);

        List<Like> likeList10 = mypageBO.getLikeList10((Integer)session.getAttribute("userId"));
        model.addAttribute("likeList10", likeList10);

        return "user/mypage";
    }


    @GetMapping("/like")
    public String like(
            Model model,
            HttpSession session
    ) {

        User user = mypageBO.getUser((Integer)session.getAttribute("userId"));
        model.addAttribute("user", user);

        List<Like> likeList = mypageBO.getLikeList((Integer) session.getAttribute("userId"));
        model.addAttribute("likeList", likeList);

        return "like/like";
    }

}

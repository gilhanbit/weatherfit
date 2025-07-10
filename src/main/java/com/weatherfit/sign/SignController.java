package com.weatherfit.sign;

import com.weatherfit.sign.domain.KakaoUserInfoDTO;
import com.weatherfit.sign.service.KakaoLoginBO;
import com.weatherfit.sign.service.SignBO;
import com.weatherfit.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Component
@RequiredArgsConstructor
@RequestMapping("/sign")
@Controller
public class SignController {

    private final SignBO signBO;
    private final KakaoLoginBO kakaoLoginBO;


    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;


    @GetMapping("/sign-in")
    public String signIn(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri;
        model.addAttribute("location", location);

        return "sign/signIn";
    }


    @GetMapping("/callback")
    public String callback(
            @RequestParam("code") String code,
            RedirectAttributes redirectAttributes
    ) {
        String accessToken = kakaoLoginBO.getAccessTokenFromKakao(code);
        KakaoUserInfoDTO userInfo = kakaoLoginBO.getUserInfo(accessToken);

        String userName = userInfo.getKakaoAccount().getName();
        String userEmail = userInfo.getKakaoAccount().getEmail();

        redirectAttributes.addFlashAttribute("userName", userName);
        redirectAttributes.addFlashAttribute("userEmail", userEmail);

        return "redirect:/sign/sign-up";
    }


    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("userLoginId");

        return "redirect:/weatherfit/main";
    }


    @GetMapping("/sign-up")
    public String signUp() {
        return "sign/signUp";
    }


    @GetMapping("/help-id")
    public String helpId() {
        return "sign/helpId";
    }


    // TODO 비동기 요청으로 수정할 것
    @PostMapping("/checkId")
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
            model.addAttribute("cantFindId", "아이디가 존재하지 않습니다.");
        }

        return "sign/checkId";
    }


    @GetMapping("help-pw")
    public String helpPw() {
        return "sign/helpPw";
    }


    @PostMapping("/checkPw")
    public String checkPw(
            @RequestParam("loginId") String loginId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model
    ){

        UserEntity user = signBO.findUserPw(loginId, name, email);

        if (user == null) {
            model.addAttribute("cantFindPw", "잘못 입력하셨습니다.");
        }

        if (user != null) {
            String newPw = RandomStringUtils.randomAlphanumeric(10);
            signBO.updatePw(loginId, name, email, newPw);
            model.addAttribute("newPw", newPw);
        }
        return "sign/extraPw";
    }

}

package com.weatherfit.sign;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.sign.service.KakaoLoginBO;
import com.weatherfit.sign.service.SignBO;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.entity.UserEntity;
import com.weatherfit.user.service.StyleBO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/sign")
@RestController
public class SignRestController {

    private final SignBO signBO;
    private final StyleBO styleBO;
    private final KakaoLoginBO kakaoLoginBO;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Void>> signIn(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpServletRequest request
    ){

        UserEntity user = signBO.getUser(loginId, password);
        Style style = styleBO.getUserStyle(user.getId());

        if (user != null) {
            // 로그인 성공 시 서버에 세션 공간을 만들어둔다.
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userEmail", user.getEmail());

            session.setAttribute("top", style.getTop());
            session.setAttribute("bottom", style.getBottom());
            session.setAttribute("shoes", style.getShoes());
            return ApiResponse.onSuccess(SuccessStatus.OK, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/id-duplicate-id")
    public ResponseEntity<ApiResponse<Map<String, Object>>> isDuplicateId(
            @RequestParam("loginId") String loginId
    ) {

        boolean isDuplicate = signBO.isDuplicateLoginId(loginId);

        Map<String, Object> result = new HashMap<>();
        result.put("isDuplicateId", isDuplicate);

        if(isDuplicate == true) {
            return ApiResponse.onSuccess(SuccessStatus.CREATED, result);
        } else {
            return ApiResponse.onFail(ErrorStatus.DUPLICATE_LOGINID);
        }
    }


    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Void>> signUp(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){

        boolean user = signBO.setUser(loginId, password, name, email);

        if(user == true) {
            return ApiResponse.onSuccess(SuccessStatus.CREATED, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.INTERNAL_ERROR);
        }
    }

}

package com.weatherfit.user;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import com.weatherfit.user.entity.UserEntity;
import com.weatherfit.user.service.StyleBO;
import com.weatherfit.user.service.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserRestController {

    private final UserBO userBO;
    private final StyleBO styleBO;

    @PostMapping("/update-info")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> infoChange(
            @RequestParam("userId") int userId,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "email", required = false) String email
    ) {

        boolean user = userBO.updateUserInfo(userId, password, email);

        if (user == true) {
            return ApiResponse.onSuccess(SuccessStatus.OK, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.INTERNAL_ERROR);
        }
    }

    @PostMapping("/style")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> style(
            @RequestParam("userId") int userId,
            @RequestParam(value = "top", required = false) String top,
            @RequestParam(value = "bottom", required = false) String bottom,
            @RequestParam(value = "shoes", required = false) String shoes
    ) {

        Style isStyle = styleBO.getUserStyle(userId);

        if (isStyle != null) {
            styleBO.updateUserStyle(userId, top, bottom, shoes);
        } else {
            styleBO.setUserStyle(userId, top, bottom, shoes);
        }

        return ApiResponse.onSuccess(SuccessStatus.OK, null);
    }
}

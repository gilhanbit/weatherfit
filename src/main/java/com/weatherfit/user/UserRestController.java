package com.weatherfit.user;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @PostMapping("/info-change")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> infoChange(
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "email", required = false) String email
    ) {


        if (user != null) {
            return ApiResponse.onSuccess(SuccessStatus.OK, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.INTERNAL_ERROR);
        }
    }
}

package com.weatherfit.product;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.product.service.ProductBO;
import com.weatherfit.user.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductRestController {

    private final ProductBO productBO;

    @GetMapping("/add")
    public ResponseEntity<ApiResponse<Void>> add(
            @RequestParam("userId") int userId,
            @RequestParam("link") String link,
            @RequestParam("image") String image,
            @RequestParam("title") String title,
            @RequestParam("lprice") int lprice
    ) {

        int success = productBO.setProduct(userId, link, image, title, lprice);

        if (success > 0) {
            return ApiResponse.onSuccess(SuccessStatus.OK, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> add(
            @RequestParam("likeId") int likeId
    ) {

        int success = productBO.delProduct(likeId);

        if (success > 0) {
            return ApiResponse.onSuccess(SuccessStatus.OK, null);
        } else {
            return ApiResponse.onFail(ErrorStatus.BAD_REQUEST);
        }
    }
}

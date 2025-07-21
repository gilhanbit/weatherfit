package com.weatherfit.product;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.product.service.ProductBO;
import com.weatherfit.user.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductRestController {

    private final ProductBO productBO;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> add(
            @RequestParam("userId") int userId,
            @RequestParam("link") String link,
            @RequestParam("image") String image,
            @RequestParam("title") String title,
            @RequestParam("lprice") int lprice,
            @RequestParam(value="category1", required = false) String category1,
            @RequestParam(value="category2", required = false) String category2,
            @RequestParam(value="category3", required = false) String category3
    ) {

        int success = productBO.setProduct(userId, link, image, title, lprice, category1, category2, category3);

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

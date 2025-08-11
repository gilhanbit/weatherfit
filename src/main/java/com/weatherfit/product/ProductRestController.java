package com.weatherfit.product;

import com.weatherfit.common.response.ApiResponse;
import com.weatherfit.common.response.ErrorStatus;
import com.weatherfit.common.response.SuccessStatus;
import com.weatherfit.product.service.ProductBO;
import com.weatherfit.user.domain.Like;
import jakarta.servlet.http.HttpSession;
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
            @RequestParam(value="category3", required = false) String category3,
            HttpSession session

    ) {

        String gender = (String) session.getAttribute("userGender");
        int age = (Integer) session.getAttribute("userAge");
        int x = (Integer) session.getAttribute("userX");
        int y = (Integer) session.getAttribute("userY");
        double tmp = (Double) session.getAttribute("tmp");


        Like like = new Like();
        like.setUserId(userId);
        like.setLink(link);
        like.setImage(image);
        like.setTitle(title);
        like.setLprice(lprice);
        like.setCategory1(category1);
        like.setCategory2(category2);
        like.setCategory3(category3);
        int addLike = productBO.setProduct(like);

        int likeId = like.getId();
        productBO.setLikeMeta(userId, likeId, gender, age, x, y, tmp, title, lprice);

        if (addLike > 0) {
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

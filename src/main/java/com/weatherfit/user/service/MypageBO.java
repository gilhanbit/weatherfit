package com.weatherfit.user.service;

import com.weatherfit.user.domain.Like;
import com.weatherfit.user.domain.MypageDTO;
import com.weatherfit.user.domain.Style;
import com.weatherfit.user.domain.User;
import com.weatherfit.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageBO {

    private final UserBO userBO;
    private final StyleBO styleBO;

    public User getUser(Integer userId) {
        return userBO.getUser(userId);
    }

    public Style getUserStyle(Integer userId) {
        Style isStyle = styleBO.getUserStyle(userId);
        if (isStyle == null) {
            Style style = new Style();
            style.setTop("미지정");
            style.setBottom("미지정");
            style.setShoes("미지정");
            return style;
        }
        return isStyle;
    }

//    public List<Like> generateUserLike(Integer userId) {
//        return likeBO.getLikeList();
//    }
}

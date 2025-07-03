package com.weatherfit.user.domain;

import com.weatherfit.user.entity.UserEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class MypageDTO {

    private UserEntity user;
    private Style style;
    private List<Like> likeList;
}

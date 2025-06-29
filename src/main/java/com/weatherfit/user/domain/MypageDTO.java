package com.weatherfit.user.domain;

import com.weatherfit.user.entity.UserEntity;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MypageDTO {

    private UserEntity user;
    private Style style;
    private Like like;
}

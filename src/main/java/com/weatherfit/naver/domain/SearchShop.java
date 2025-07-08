package com.weatherfit.naver.domain;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SearchShop {

    private String title;
    private String link;
    private String image;
    private Integer lprice;

}

package com.weatherfit.naver.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class SearchShop {

    private String title;
    private String link;
    private String image;
    private Integer lprice;
    private String category1;
    private String category2;
    private String category3;
    private LocalDateTime createdAt;
}

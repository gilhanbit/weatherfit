package com.weatherfit.user.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class Like {

    private int id;
    private String userId;
    private String title;
    private String link;
    private String image;
    private int lowPrice;
    private int highPrice;
    private String brand;
    private LocalDateTime createdAt;
}

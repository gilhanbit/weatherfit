package com.weatherfit.user.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class Like {

    private int id;
    private int userId;
    private String link;
    private String image;
    private String title;
    private int lprice;
    private LocalDateTime createdAt;

}

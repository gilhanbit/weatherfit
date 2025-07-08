package com.weatherfit.weather.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class ShortFcst {

    private int id;
    private String fcstDate;
    private Double tmp;
    private Double tmn;
    private Double tmx;
    private Long nx;
    private Long ny;
    private LocalDateTime createdAt;

    private Integer tmpDiff;
    private String advice;
}

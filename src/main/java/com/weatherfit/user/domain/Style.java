package com.weatherfit.user.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class Style {

    private int id;
    private String userId;
    private String top;
    private String bottom;
    private String shoes;
    private LocalDateTime createdAt;
}

package com.weatherfit.user.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class User {

    private int id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private int x;
    private int y;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

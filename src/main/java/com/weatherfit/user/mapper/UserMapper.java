package com.weatherfit.user.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public void updatePw(String loginId, String name, String email, String hashedPassword);
}

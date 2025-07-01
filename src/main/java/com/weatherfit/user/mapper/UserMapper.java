package com.weatherfit.user.mapper;

import com.weatherfit.user.domain.User;
import com.weatherfit.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User selectUserByUserId(Integer userId);
    public void updatePw(String loginId, String name, String email, String hashedPassword);
    public boolean updateUserInfo(int userId, String hashedPassword, String email);
}

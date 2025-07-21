package com.weatherfit.user.service;

import com.weatherfit.user.domain.User;
import com.weatherfit.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserBO {

    private final UserMapper userMapper;

    public User getUser(Integer userId) {
        return userMapper.selectUserByUserId(userId);
    }


    public void setLocation(Integer userId, Integer x, Integer y) {
        userMapper.updateLocation(userId, x, y);
    }


    public boolean updateUserInfo(int userId, String password, String email) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        return userMapper.updateUserInfo(userId, hashedPassword, email);
    }
}

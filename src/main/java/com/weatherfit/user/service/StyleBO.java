package com.weatherfit.user.service;

import com.weatherfit.user.domain.Style;
import com.weatherfit.user.mapper.StyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StyleBO {

    private final StyleMapper styleMapper;

    public Style getUserStyle(Integer userId) {
        return styleMapper.selectUserStyle(userId);
    }

    public boolean setUserStyle(int userId, String top, String bottom, String shoes) {
        return styleMapper.insertUserStyle(userId, top, bottom, shoes);
    }

    public boolean updateUserStyle(int userId, String top, String bottom, String shoes) {
        return styleMapper.updateUserStyle(userId, top, bottom, shoes);
    }
}

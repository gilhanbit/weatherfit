package com.weatherfit.test;

import com.weatherfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestBO {

    private final TestMapper testMapper;

    public List<User> getUser(){
        return testMapper.selectUser();
    }
}

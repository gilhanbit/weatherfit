package com.weatherfit.test;

import com.weatherfit.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    public List<User> selectUser();
}

package com.weatherfit.test;

import com.weatherfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final TestBO testBO;

    @ResponseBody
    @GetMapping("/test1")
    public String test1(){
        return "<h2>test</h2>";
    }

    @GetMapping("/test2")
    public String test2(){
        return "test/test";
    }

    @ResponseBody
    @GetMapping("/test3")
    public List<User> test3(){
        return testBO.getUser();
    }

    @ResponseBody
    @GetMapping("/test4")
    public Map<String, Object> test4(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", 333);
        map.put("b", 2333);
        map.put("c", 3334);
        return map;
    }
}

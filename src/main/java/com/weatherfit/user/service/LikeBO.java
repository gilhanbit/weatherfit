package com.weatherfit.user.service;

import com.weatherfit.user.domain.Like;
import com.weatherfit.user.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeBO {

    private final LikeMapper likeMapper;

    public int setProduct(int userId, String link, String image, String title, int lprice) {
        return likeMapper.insertProduct(userId, link, image, title, lprice);
    }


    public List<Like> getLikeList10(int userId) {
        return likeMapper.selectLikeList10(userId);
    }


    public List<Like> getLikeList(int userId) {
        return likeMapper.selectLikeList(userId);
    }


    public int delProduct(int likeId) {
        return likeMapper.delProduct(likeId);
    }
}

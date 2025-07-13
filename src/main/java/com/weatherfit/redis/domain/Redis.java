package com.weatherfit.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "MemberToken", timeToLive = 3600 * 24 * 14)
@AllArgsConstructor
@Data
public class Redis {

    @Id
    private String userId;
    private String refreshToken;
}

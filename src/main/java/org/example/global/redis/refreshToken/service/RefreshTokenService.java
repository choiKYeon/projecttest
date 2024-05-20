package org.example.global.redis.refreshToken.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;

    public RefreshTokenService(StringRedisTemplate redisTemplate) { // @Lazy 주석 적용
        this.redisTemplate = redisTemplate;

    }

    //   redis 저장 구문
    public void saveRefreshToken(String refreshToken, String username) {
        redisTemplate.opsForValue().set(refreshToken, username, Duration.ofSeconds(10));
    }

    //   redis 삭제구문
    public void deleteRefreshToken(String oldToken) {
        redisTemplate.delete(oldToken);
    }

    //    redis 조회 구문
    public String getUsernameFromRefreshToken(String token) {

        return redisTemplate.opsForValue().get(token);
    }
}

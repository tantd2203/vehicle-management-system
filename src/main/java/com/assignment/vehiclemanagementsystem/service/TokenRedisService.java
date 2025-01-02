package com.assignment.vehiclemanagementsystem.service;

import com.assignment.vehiclemanagementsystem.entity.UserSessionToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class TokenRedisService {

    private static final String HASH_KEY = "UserSessionToken";


    private final RedisTemplate redisTemplate;

    public UserSessionToken saveToken(UserSessionToken userSessionToken) {
        redisTemplate.opsForHash().put(HASH_KEY, userSessionToken.getUserId(), userSessionToken);
        return userSessionToken;
    }

    public List<UserSessionToken> getAllToken() {
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public UserSessionToken findTokenById(int id) {
        return (UserSessionToken) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteToken(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "UserSessionToken deleted successfully!";
    }

}

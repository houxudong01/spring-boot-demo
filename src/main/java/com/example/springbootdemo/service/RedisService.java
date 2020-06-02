package com.example.springbootdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author HouXudong
 * @date 2019-09-05
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            logger.error("redis exception: ", e);
            return false;
        }
        return true;
    }

    public Boolean set(String key, Object value, Long timeout) {
        try {
            this.redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(timeout));
        } catch (Exception e) {
            logger.error("redis exception: ", e);
            return false;
        }
        return true;
    }

    public Object get(String key) {
        Object value = this.redisTemplate.opsForValue().get(key);
        return value;
    }

}

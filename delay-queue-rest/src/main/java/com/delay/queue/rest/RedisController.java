package com.delay.queue.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: redis 控制层
 * @author: 贾诩
 * @date: 2021/2/14 23:11
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/get")
    private String get(HttpServletRequest request) {
        String key = request.getParameter("key");
        return stringRedisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/set")
    private String set(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        stringRedisTemplate.opsForValue().set(key, value);
        return "sucess";
    }
}
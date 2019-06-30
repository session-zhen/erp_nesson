package com.hqyj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	public void setCache(String key,String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}
	public String getCache(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}
	public void removeCache(String key) {
		stringRedisTemplate.delete(key);
		
	}
	public boolean hasCache(String key) {
		return stringRedisTemplate.hasKey(key);
	}
}

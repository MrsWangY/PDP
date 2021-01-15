package com.pdp.manager.redis;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @author LIXr
 * @Description
 * @date 2020/12/10
 */
@Service
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

	//添加hash,需要hash名和存储的键值对Map  
    public void setHash(String hashName,Map<String,String> map) {
    	redisTemplate.opsForHash().putAll(hashName,map);
	}
    
	//Springboot的启动器main方法上需要加上@EnableCaching开启缓存，使用了@Cacheable注解后，缓存的值将被存入redis数据库中
	//缓存名可以为RedisConfig中自定义的缓存名，键生成器为RedisConig中自定义的键生成器，也可以自己自定义缓存key名
	@Cacheable(cacheNames = "users",keyGenerator ="myKeyGenerator")
    //从redis中获取map
	public Map<Object,Object> getHash(String hashName){
		if (redisTemplate.hasKey(hashName)) {
//        	System.out.println(redisTemplate.opsForHash().entries(hashName));
        	return redisTemplate.opsForHash().entries(hashName);
    	}else {
        	return null;
    	}
	}
}

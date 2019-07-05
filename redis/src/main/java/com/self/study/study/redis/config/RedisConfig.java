package com.self.study.study.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@Profile("spring.redis")
public class RedisConfig extends CachingConfigurerSupport {

    @Value("spring.redis.host")
    private   String   host;


    @Value("spring.redis.port")
    private   int   port;

    // 获取redis连接
    @Bean
    public StringRedisTemplate  stringRedisTemplate(RedisConnectionFactory   redisConnectionFactory){
        StringRedisTemplate redisTemplate= new StringRedisTemplate();
        // 获取redis连接
       redisTemplate.setConnectionFactory(redisConnectionFactory);
       //  redis序列化方式
       /* Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);*/
        JdkSerializationRedisSerializer jdkSerialization = new JdkSerializationRedisSerializer();
        //  字符型数据的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //  设置key-value的序列化方式的
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);

        return   redisTemplate;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory   redisConnectionFactory){

        RedisTemplate  redisTemplate= new RedisTemplate();
        // 获取redis连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //  redis序列化方式
        /* Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);*/
        JdkSerializationRedisSerializer jdkSerialization = new JdkSerializationRedisSerializer();
        //  字符型数据的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //  设置key-value的序列化方式的
        // hash类型的序列化放肆
        redisTemplate.setHashKeySerializer(jdkSerialization);
        redisTemplate.setHashValueSerializer(jdkSerialization);

        return   redisTemplate;
    }


    //  获取到redis连接工厂方式
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
         return  new  LettuceConnectionFactory(host,port);
    }

    //配置 redis緩存工具
    @Bean
    public CacheManager cacheManager (RedisConnectionFactory  redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return cacheManager;
    }

}

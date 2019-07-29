package com.self.study.redis.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
@EnableCaching
@Profile("dev")
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


    // 返回redisson的config 配置类信息
    @Bean
   // @ConditionalOnProperty({"spring.redis.host","spring.redis.port"})
    //@ConditionalOnClass(Config.class)
    public RedissonClient redissonClient(Config  config){
       /* Config  config= new Config();
         config.useSingleServer().setAddress("redis://" + host + ":" + port);*/
        RedissonClient redissonClient = Redisson.create(config);
        return   redissonClient;
    }

    @Bean
    public   Config  config(){
        Config  config= new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useSingleServer().setAddress("redis://localhost:6379");
       // config.useSingleServer().set( "redis://"+host + ":" + port);
        return   config;
    }

    //  创建默认的布隆过滤器,对应的可以使用相关的布隆过滤器的实现逻辑和体现
    @Bean
    public BloomFilter bloomFilter(){
           return BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 100000,0.3);
    }

    //  对应的还需要相关的哨兵的配置信息.需要关注一下
    @Bean
    public JedisCluster jedisCluster(){
        Set set=  new HashSet<>();
        Set<HostAndPort> CLUSTER_HOST_AND_PORTS = new HashSet(){{
            add(new HostAndPort("127.0.0.1", 6380));
            add(new HostAndPort("127.0.0.1", 6381));
            add(new HostAndPort("127.0.0.1", 6382));
            add(new HostAndPort("127.0.0.1", 6383));
            add(new HostAndPort("127.0.0.1", 6384));
            add(new HostAndPort("127.0.0.1", 6385));
        }};
        JedisCluster  jedisCluster= new JedisCluster(CLUSTER_HOST_AND_PORTS);
        return  jedisCluster;
    }

    // 配置对应的redis分片集群的配置信息
    @Bean
    public ShardedJedisPool shardedJedisPool(){
        JedisPoolConfig  jedisPoolConfig= new JedisPoolConfig();
        //  下面是JedisShardInfo 分片集群信息
        List<JedisShardInfo> shards = new ArrayList<>();
        JedisShardInfo jedisShardInfo_1=  new JedisShardInfo("127.0.0.1",6380,3000);
        JedisShardInfo jedisShardInfo_2=  new JedisShardInfo("127.0.0.1",6381,3000);
        JedisShardInfo jedisShardInfo_3=  new JedisShardInfo("127.0.0.1",6382,3000);
        JedisShardInfo jedisShardInfo_4=  new JedisShardInfo("127.0.0.1",6383,3000);
        JedisShardInfo jedisShardInfo_5=  new JedisShardInfo("127.0.0.1",6384,3000);
        JedisShardInfo jedisShardInfo_6=  new JedisShardInfo("127.0.0.1",6385,3000);
        shards.add(jedisShardInfo_1);
        shards.add(jedisShardInfo_2);
        shards.add(jedisShardInfo_3);
        shards.add(jedisShardInfo_4);
        shards.add(jedisShardInfo_5);
        shards.add(jedisShardInfo_6);
        return  new ShardedJedisPool(jedisPoolConfig,shards);
    }


}

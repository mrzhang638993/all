package com.self.study.redis;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.self.study.redis.bo.User;

import java.util.concurrent.TimeUnit;

public class GuavaLocalCache {

    public static void main(String[] args) {
        Cache  cache= CacheBuilder.newBuilder()
                .maximumSize(100)
                .concurrencyLevel(8)
                .expireAfterWrite(1, TimeUnit.MILLISECONDS)
                .recordStats()
                .build(new CacheLoader<String, User>(){
                    @Override
                    public User load(String  o) throws Exception {
                        return null;
                    }
                });
        //  可用使用来进行相关的本地缓存的处理实现的。还可以使用相关的concurrenthashmap实现本地缓存的操作的。



    }
}

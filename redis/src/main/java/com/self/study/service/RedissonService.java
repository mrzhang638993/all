package com.self.study.service;


import org.redisson.api.RedissonClient;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RedissonService {


    @Autowired
    private RedissonClient redissonClient;

    //  获取字符串信息
    public <T> RBucket<T> getRBucket(String objectName) {
        RBucket<T> bucket = redissonClient.getBucket(objectName);
          return   bucket;
    }

    //  获取map对象
    public <K, V> RMap<K, V> getRMap(String objectName) {
        RMap<K, V> map = redissonClient.getMap(objectName);
        return  map;
    }

    //  获取有序集合
    public <V> RSortedSet<V> getRSortedSet(String objectName) {
        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return   sortedSet;
    }

    //  获取set集合
    public <V> RSet<V> getRSet(String objectName) {
        RSet<V> set = redissonClient.getSet(objectName);
        return    set;
    }

    //  获取 list集合
    public <V> RList<V> getRList(String objectName) {
        RList<V> list = redissonClient.getList(objectName);
        return   list;
    }

    //  获取队列
    public <V> RQueue<V> getRQueue(String objectName) {
        RQueue<V> queue = redissonClient.getQueue(objectName);
        return   queue;
    }

    //  获取双端队列
    public <V> RDeque<V> getRDeque(String objectName) {
        RDeque<V> deque = redissonClient.getDeque(objectName);
        return   deque;
    }

    //  获取锁
    public RLock getRLock(String objectName) {
        RLock lock = redissonClient.getLock(objectName);
        return   lock;
    }

    //  获取读写锁
    public RReadWriteLock getRWLock(String objectName) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(objectName);
        return   readWriteLock;
    }

    // 获取原子数
    public RAtomicLong getRAtomicLong(String objectName) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(objectName);
        return   atomicLong;
    }

    //  获取计数锁
    public RCountDownLatch getRCountDownLatch(String objectName) {
        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch(objectName);
        return   countDownLatch;
    }

    //  获取消息的topic
   public    RTopic getRTopic(String objectName) {
        RTopic  topic = redissonClient.getTopic(objectName);
        return   topic;
    }
}
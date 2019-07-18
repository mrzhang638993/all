package com.self.study.service;


import com.self.study.redis.bo.User;
import com.self.study.spring.CustomerAnno;
import org.redisson.api.RedissonClient;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;
    @Cacheable(cacheManager = "cacheManager",value = "cache-1",key="#userName")
      public  void   testCacheInput(String userName ){
       /* User user= new User();
        user.setAge(18);
        user.setName(userName);*/
      //  核心的逻辑是这个的  //  userDao.findByUserName(userName);
       /* System.out.println(user.toString());*/
      }
    @CacheEvict(cacheManager = "cacheManager",value = "cache-1",key="#userName")
    public  void  testDelete(String userName){
        //  userDao.deleteByUserName(userName);
        System.out.println("从缓存中删除数据信息");
    }
    @CachePut(cacheManager = "cacheManager",value = "cache-1",key="#user.userName",condition = "#user.userName!=null")
    public  void  testInput(User  user){
        //  userDao.insert(user);
        System.out.println("数据库执行了更新操作，对应的数据信息如下"+user.toString());
    }

    /*
    @CachePut(value = "user", key = "#user.username")
    public void save(User user) {
        userDao.save(user);
    }
    @Cacheable(value = "user", key = "#id")
    public User findById(int id) {
        User user = userDao.findById(id);
        return user;
    }
    */
/**
     * 查询结果不为空 才放进缓存 unless
     * @return
     *//*
    @Cacheable(value = "defaultCache",unless = "#result==null")
    public List<User> listAll() {
        return userDao.listAll();
    }
*/

    @CustomerAnno(key = "#userName")
    public  User  testConsumer(String  userName){
        //  userDao.findByUserName(userName);
        User user = null;
        user = new User();
        user.setName(userName);
        System.out.println("从数据库中读取到值：" + user);
        return user;
    }
}

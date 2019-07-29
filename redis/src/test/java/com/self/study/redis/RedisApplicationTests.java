package com.self.study.redis;

import com.self.study.redis.bo.User;
import com.self.study.service.RedissonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testValueOperation() {
        //stringRedisTemplate.opsForValue().set("good", "boy");
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("test", "good   boy  test");
      /*  User user = new User();
        user.setName("zhangchenglong");
        user.setAge(19);
        redisTemplate.opsForHash().put("user", "userInput", user);
        User user1 = (User) redisTemplate.opsForHash().get("user", "userInput");
        System.out.println(user.toString());*/
    }


    @Autowired
    private RedissonService redissonService;


    @Test
    public void testRedisson() {
        RBucket<String> rBucket = redissonService.getRBucket("testBucket");
        rBucket.set("onlyForTest");
        rBucket.setAsync("测试");
        System.out.println(rBucket.get());

    }

    @Test
    public void testList() {
        //  对应的将元素存储到list中进行操作的
        RList<Object> mylist = redissonService.getRList("mylist");
        mylist.add("first");
        mylist.add("second");
        mylist.add("third");
        mylist.add("forth");
        mylist.add("fifth");
        System.out.println(Arrays.toString(mylist.toArray()));
    }

    @Test
    public void testMap() {
        //  对应的将元素存储到list中进行操作的
        RMap<Object, Object> myMap = redissonService.getRMap("myMap");
        myMap.put("map1", "value1");
        myMap.put("map2", "value2");
        myMap.put("map3", "value3");
        myMap.put("map4", "value4");
        myMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }

    @Test
    public void testGetRAtomicLong() {
        RAtomicLong rAtomicLong = redissonService.getRAtomicLong("testAtomicLong");
        rAtomicLong.set(100);
        System.out.println(rAtomicLong.addAndGet(200));
        System.out.println(rAtomicLong.decrementAndGet());
        System.out.println(rAtomicLong.get());
    }

    @Test
    public void testGetRCountDownLatch() throws InterruptedException {
        RCountDownLatch rCountDownLatch = redissonService.getRCountDownLatch("testCountDownLatch");
        System.out.println(rCountDownLatch.getCount());
        System.out.println(rCountDownLatch.getCount());
        rCountDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println(rCountDownLatch.getCount());
    }

    @Test
    public void testGetRTopicPub() throws InterruptedException {
        RTopic rTopic = redissonService.getRTopic("testTopic");
        rTopic.publish("hello  everyone");
        //  发布消息之后，让消费者不在等待
       RCountDownLatch testCountDownLatch = redissonService.getRCountDownLatch("testCountDownLatch");
        testCountDownLatch.countDown();
    }

    @Test
    public void testGetRTopicSub() throws InterruptedException {
        RTopic rTopic = redissonService.getRTopic("testTopic");
        rTopic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                System.out.println(charSequence);
                System.out.println(s);
            }
        });
        //  发布消息之后，让消费者不在等待*/
        RCountDownLatch testCountDownLatch = redissonService.getRCountDownLatch("testCountDownLatch");
        testCountDownLatch.trySetCount(1);
        testCountDownLatch.countDown();
    }

    //  下面对应的就是分布式锁的实现的。调用相关的接口即可实现相关的额分布式锁的操作的
    @Test
    public   void   testLock(){
        for (int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RLock test = redissonService.getRLock("lock");
                     test.lock();
                    RAtomicLong testAtomicLong = redissonService.getRAtomicLong("good");
                    System.out.println(testAtomicLong.get());
                   /* System.out.println(redissonService.getRAtomicLong("testAtomicLong") );
                   redissonService.getRAtomicLong("testAtomicLong").addAndGet(10);*/
                   testAtomicLong.addAndGet(10L);
                    test.unlock();
                }
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void  testCacheInput(){
        redissonService.testCacheInput("mrzhang");
    }

    @Test
    public  void testDelete( ){
        redissonService.testDelete("mrzhang");
    }

    @Test
    public   void   testInput(){
        User  user= new User();
        user.setAge(20);
        user.setName("zhangchenglong");
        redissonService.testInput(user);
    }

    @Test
    public  void   testConsumer(){
        redissonService.testConsumer("mrzhang");
    }



    /*
       日志输出如下：
       test add result: true
       test addMulti result: [false, true]
       test exists result: true
       test existsMulti result: [true, false]
       test delete result: true
    */

    @Autowired
    private BloomOperations bloomOperations;


    public void testBloomFilter() throws Exception {
        String key = "TEST";

        // 1.创建布隆过滤器
        bloomOperations.createFilter(key, 0.01, 100);

        // 2.添加一个元素
        Boolean foo = bloomOperations.add(key, "foo");
        log.info("test add result: {}", foo);

        // 3.批量添加元素
        Boolean[] addMulti = bloomOperations.addMulti(key, "foo", "bar");
        log.info("test addMulti result: {}", Arrays.toString(addMulti));

        // 4.校验一个元素是否存在
        Boolean exists = bloomOperations.exists(key, "foo");
        log.info("test exists result: {}", exists);

        // 5.批量校验元素是否存在
        Boolean[] existsMulti = bloomOperations.existsMulti(key, "foo", "foo1");
        log.info("test existsMulti result: {}", Arrays.toString(existsMulti));

        // 6.删除布隆过滤器
        Boolean delete = bloomOperations.delete(key);
        log.info("test delete result: {}", delete);
    }
}

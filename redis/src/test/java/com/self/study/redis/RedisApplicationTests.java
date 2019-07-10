package com.self.study.redis;

import com.self.study.redis.bo.User;
import com.self.study.service.RedissonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
      /*  ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("test","good   boy  test");*/
        User user = new User();
        user.setName("zhangchenglong");
        user.setAge(19);
        redisTemplate.opsForHash().put("user", "userInput", user);
        User user1 = (User) redisTemplate.opsForHash().get("user", "userInput");
        System.out.println(user.toString());
    }


   @Autowired
    private RedissonService  redissonService;

    @Test
    public  void   testRedisson(){
       RBucket<String> rBucket = redissonService.getRBucket("user");
        System.out.println(rBucket.get());

    }

    @Test
    public   void   testCreate(){
      /*  Config config= new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + 6379);
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println(redissonClient);*/
    }

}

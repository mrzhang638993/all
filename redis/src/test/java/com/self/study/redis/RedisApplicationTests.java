package com.self.study.redis;

import com.self.study.redis.bo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
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
}

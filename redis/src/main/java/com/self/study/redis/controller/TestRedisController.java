package com.self.study.redis.controller;

import com.self.study.redis.domain.UserVo;
import com.self.study.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
public class TestRedisController {

    @Resource
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Resource
    private RedisService redisService;


    @Autowired
    private   RedisTemplate  redisTemplate;
    @GetMapping("/add")
    public  void   test(){
      /*  UserVo  userVo= new UserVo();
        userVo.setName("zhangcl");
        userVo.setAddress("湖北武汉");
        userVo.setAge(17);
       redisTemplate.opsForHash().putAll(userVo,new HashMap());*/

    }


}

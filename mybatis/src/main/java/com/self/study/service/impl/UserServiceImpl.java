package com.self.study.service.impl;


import com.self.study.mapper.UserMapper;
import com.self.study.domain.User;
import com.self.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zl on 2015/8/27.
 */

@Service
@Transactional
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer id) {
        User  user= new User();
        user.setId(2);
        user.setMake(true);
        user.setPassword("mrzhang");
        user.setUsername("good");
        int insert = userMapper.insert(user);
        return   user;
    }

    @Override
    public Integer batchInsert(List<User> list) {
         List  list1= new ArrayList();
         for (int i=0;i<1000;i++){
             User user= new User();
             Random  random= new Random();
             int randowId = random.nextInt(100000000);
             user.setId(randowId);
             user.setMake(true);
             user.setPassword("mrzhang");
             user.setUsername("zhangchenglong");
             list1.add(user);
         }
         //userMapper.doBatchInsert(list1);
        userMapper.doBatchInsert(list1);
         return list1.size();
    }
}

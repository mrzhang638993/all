package com.self.study.service.impl;


import com.self.study.mapper.UserMapper;
import com.self.study.domain.User;
import com.self.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zl on 2015/8/27.
 */

@Service
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
        /*return userMapper.selectByPrimaryKey(id);*/
    }
}
